package com.example.conduit.ui.newArticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.api.models.entity.CreateArticleCreds
import com.example.api.models.request.CreateArticleRequest
import com.example.conduit.databinding.FragmentNewArticleBinding
import kotlinx.coroutines.launch

class NewArticleFragment : Fragment() {

    private var _binding: FragmentNewArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var newArticleViewModel: NewArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newArticleViewModel =
            ViewModelProvider(this).get(NewArticleViewModel::class.java)
        _binding = FragmentNewArticleBinding.inflate(inflater, container, false)

        val richTextEditor = binding.articleEditor
        binding.articleToolbar.apply {
            editor = richTextEditor
        }

        setOnClickBtnArticleSubmit()

        return binding.root
    }

    private fun setOnClickBtnArticleSubmit() {

        binding.btnSubmitArticle.setOnClickListener {
            lifecycleScope.launch {
                val result = newArticleViewModel.uploadArticle(
                    CreateArticleRequest(
                        CreateArticleCreds(
                            binding.articleEditor.getCachedHtml(),
                            binding.etArticleDescription.text.toString(),
                            null,
                            binding.etArticleTitle.text.toString()
                        )
                    )
                ).await()



                if (result.errorBody() == null) {
                    Toast.makeText(context, "Article Submitted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Error : ${result.errorBody()}", Toast.LENGTH_LONG)
                        .show()
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}