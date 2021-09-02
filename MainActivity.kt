package com.example.bfaa_submission_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bfaa_submission_1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

   /* companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
*/
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var orgsViewModel: OrgsViewModel
    private lateinit var adapter: UserAdapter
    /*private val list = ArrayList<User>()*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = "Git User"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        showList()


//        orgsViewModel.setUserOrg()


        showLoading(false)
    }

    private fun showList() {
        binding.listLayout.layoutManager = LinearLayoutManager(this)


        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.setUserList()
        mainViewModel.getUserList().observe(this, {gitUser ->
            if (gitUser != null) {
                adapter.setData(gitUser)
            }
        })

        binding.listLayout.adapter = adapter
        orgsViewModel.setUserOrg()
        orgsViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(OrgsViewModel::class.java)
        orgsViewModel.getUserOrg().observe(this, {gitUserOrg ->
            if (gitUserOrg != null) {
                adapter.setData(gitUserOrg)
            }
        })


    }



    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }
}
