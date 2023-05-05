package com.example.todolist

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.TodoAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.dto.TodoDTO
import com.example.todolist.viewModel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var todoViewModel: TodoViewModel
    lateinit var todoAdapter: TodoAdapter
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_item_delete ->{
                Toast.makeText(this, "삭제", Toast.LENGTH_SHORT).show()
                todoViewModel.todoList.value!!.forEach {
                    if (it.isChecked) {
                        todoViewModel.delete(it)
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        todoViewModel.todoList.observe(this){
            todoAdapter.update(it)
        }

        todoAdapter = TodoAdapter(this)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = todoAdapter

        todoAdapter.setItemCheckBoxClickListener(object:TodoAdapter.ItemCheckBoxClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
               CoroutineScope(Dispatchers.IO).launch {
                   val todo = todoViewModel.getOne(itemId)
                   todo.isChecked = !todo.isChecked
                   todoViewModel.update(todo)
               }
            }

        })
        binding.addBtn.setOnClickListener{
            val intent = Intent(this,EditActivity::class.java).apply {
                putExtra("type","ADD")
            }
            requestActivity.launch(intent)
        }
        todoAdapter.setItemClickListener(object:TodoAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = todoViewModel.getOne(itemId)

                    val intent = Intent(this@MainActivity,EditActivity::class.java).apply {
                        putExtra("type","EDIT")
                        putExtra("item",todo)
                    }
                    requestActivity.launch(intent)

                }
            }
        })
    }
    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val todoDTO : TodoDTO
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                todoDTO = it.data?.getSerializableExtra("todoDTO",TodoDTO::class.java)!!
            } else {
                todoDTO = (it.data?.getSerializableExtra("todoDTO") as? TodoDTO)!!
            }

            when(it.data?.getIntExtra("flag", -1)) {
                0 -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.insert(todoDTO)
                    }
                    Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                1->{
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.update(todoDTO)
                    }
                    Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}



