package com.anandy.motoechargetracker.ui.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.anandy.motoechargetracker.*
import com.anandy.motoechargetracker.databinding.FragmentMainBinding
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.ui.common.EventObserver
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by lazy {
        getViewModel { MainViewModel(BatteryChargeRepository(app)) }
    }
    private lateinit var recordsAdapter: BatteryChargeAdapter
    private val coarsePermissionRequester by lazy {
        PermissionRequester(activity!!)
    }
    private val chooserRequestCode = 123
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        (activity as AppCompatActivity).setTitle(R.string.title_activity_main)

        with(mainViewModel) {
            toastMessage.observe(viewLifecycleOwner, EventObserver { message -> toast(message) })
            navigateToRegister.observe(viewLifecycleOwner, EventObserver { id ->
                val action = MainFragmentDirections.actionMainFragmentToRegisterChargeFragment(id)
                navController.navigate(action)
            })
            removeCharge.observe(viewLifecycleOwner, EventObserver { charge ->
                val dialogBuilder = AlertDialog.Builder(this@MainFragment.context)
                dialogBuilder.setTitle("Delete")
                dialogBuilder.setMessage("Do you want to remove the record?")
                dialogBuilder.setPositiveButton("Yes") { _, _ ->
                    mainViewModel.onRemoveItem(charge)
                }
                dialogBuilder.setNegativeButton("No") { _, _ -> }
                dialogBuilder.show()
            })

            items.observe(viewLifecycleOwner, Observer { value ->
                Log.d("MotoE", "Items observer size ${value}")
                //recordsAdapter.items = value
            })
        }

        recordsAdapter = BatteryChargeAdapter(mainViewModel::onClickedItemAction)
        batteryChargeRecycler.adapter = recordsAdapter

        Log.d("MotoE", "ViewModel value ${mainViewModel}")
        binding.apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainFragment

            fab.setOnClickListener { _ ->
                val action = MainFragmentDirections.actionMainFragmentToRegisterChargeFragment()
                navController.navigate(action)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.export_records -> {
                //MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL).
                val exportFolder =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        ?.absolutePath
                coarsePermissionRequester.request(WRITE_EXTERNAL_STORAGE) {
                    mainViewModel.onExportRecords(
                        exportFolder!!
                    )
                }
            }
            R.id.import_records -> {
                coarsePermissionRequester.request(READ_EXTERNAL_STORAGE) {
                    val intent = Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT)

                    startActivityForResult(
                        Intent.createChooser(intent, "Seleccionar un archivo"),
                        chooserRequestCode
                    )
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == chooserRequestCode && resultCode == RESULT_OK) {
            val uri = data?.data!!
            val br: BufferedReader
            try {
                br = BufferedReader(
                    InputStreamReader(
                        this.activity!!.contentResolver.openInputStream(uri)
                    )
                )
                val content = br.readText()
                br.close()
                mainViewModel.onImportRecords(content)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

