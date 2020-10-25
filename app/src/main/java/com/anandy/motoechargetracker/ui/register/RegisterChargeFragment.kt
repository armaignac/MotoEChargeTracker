package com.anandy.motoechargetracker.ui.register

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.anandy.motoechargetracker.*
import com.anandy.motoechargetracker.data.repository.BatteryChargeRepository
import com.anandy.motoechargetracker.database.RoomDataSource
import com.anandy.motoechargetracker.ui.common.DatePickerFragment
import com.anandy.motoechargetracker.databinding.FragmentRegisterChargeBinding
import com.anandy.motoechargetracker.domain.BatteryCharge
import com.anandy.motoechargetracker.ui.common.EventObserver
import com.anandy.motoechargetracker.usecases.FindCharge
import com.anandy.motoechargetracker.usecases.SaveCharge

import java.util.*


class RegisterChargeFragment : Fragment() {

    private lateinit var binding: FragmentRegisterChargeBinding
    private lateinit var registerChargeViewModel: RegisterChargeViewModel
    private lateinit var datePicker: DatePickerFragment
    private var charge: BatteryCharge? = null
    private val args: RegisterChargeFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRegisterChargeBinding>(
            inflater,
            R.layout.fragment_register_charge,
            container,
            false
        )
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = view.findNavController()

        (activity as AppCompatActivity).setTitle(R.string.title_activity_register_charge)

        registerChargeViewModel = getViewModel {
            RegisterChargeViewModel(
                SaveCharge(
                    BatteryChargeRepository(RoomDataSource(app.db))
                ),
                FindCharge(BatteryChargeRepository(RoomDataSource(app.db)))
            )
        }

        datePicker = DatePickerFragment() { selectedDate ->
            registerChargeViewModel.onDateSelected(selectedDate)
        }

        datePicker.selectedDate = Calendar.getInstance().time

        registerChargeViewModel.apply {
            chargeDate.observe(
                viewLifecycleOwner,
                Observer { date -> datePicker.selectedDate = date })
            charge.observe(
                viewLifecycleOwner,
                Observer { modelCharge -> this@RegisterChargeFragment.charge = modelCharge })
            navigateToMain.observe(viewLifecycleOwner, EventObserver { _ ->
                val action =
                    RegisterChargeFragmentDirections.actionRegisterChargeFragmentToMainFragment()
                navController.navigate(action)
            })
            onLoadCharge(args.id)
        }

        binding.apply {
            viewModel = registerChargeViewModel
            lifecycleOwner = this@RegisterChargeFragment
            textDateCharge.setOnClickListener {
                datePicker.show(this@RegisterChargeFragment.parentFragmentManager, "datePicker")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.register_charge, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_charge -> {
                if (binding.textKilometers.text.isNotEmpty() && binding.textKilometers.text.toString()
                        .toInt() > 0
                ) {
                    if (charge == null) {
                        charge = BatteryCharge(
                            0,
                            binding.textKilometers.text.toString().toInt(),
                            datePicker.selectedDate
                        )
                    } else {
                        charge?.kilometers = binding.textKilometers.text.toString().toInt()
                        charge?.date = datePicker.selectedDate
                    }
                    registerChargeViewModel.onSave(charge!!)
                } else {
                    toast("Los kilometros deben ser mayor que 0")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}