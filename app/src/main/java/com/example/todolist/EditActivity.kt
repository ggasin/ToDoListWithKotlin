package com.example.todolist

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.databinding.ActivityEditBinding
import com.example.todolist.dto.TodoDTO
import java.text.SimpleDateFormat

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private var todo: TodoDTO? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var type = intent.getStringExtra("type")
        if (type.equals("ADD")) {
            binding.btnSave.text = "추가하기"
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                todo = intent.getSerializableExtra("item",TodoDTO::class.java)!!
            } else {
                todo = intent.getSerializableExtra("item") as? TodoDTO
            }
            binding.etTodoTitle.setText(todo!!.title)
            binding.etTodoContent.setText(todo!!.content)
            binding.btnSave.text = "수정하기"

        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTodoTitle.text.toString()
            val content = binding.etTodoContent.text.toString()
            val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis())

            if(type.equals("ADD")){
                if(title.isNotEmpty() && content.isNotEmpty()){
                    val todoDTO = TodoDTO(0,title,content,timeStamp,false)
                    val intent = Intent().apply {
                        putExtra("todoDTO",todoDTO)
                        putExtra("flag",0) //flag는 단순 구분을 위함으로 0일 경우 "추가"처리를, 1일 경우 "수정"처리를 하도록 분기를 나눌 것입니다.
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }

            } else {
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    val todoDTO = TodoDTO(todo!!.id, title, content, timeStamp, todo!!.isChecked)
                    val intent = Intent().apply {
                        putExtra("todoDTO", todoDTO)
                        putExtra("flag", 1)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }

            }

        }
    }
}