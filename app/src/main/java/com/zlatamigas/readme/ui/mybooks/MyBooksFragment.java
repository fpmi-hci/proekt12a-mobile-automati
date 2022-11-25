package com.zlatamigas.readme.ui.mybooks;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.UserController;
import com.zlatamigas.readme.controller.apimodel.response.AuthorResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.BookContentResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.BookResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.CartResponseAPIModel;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.customview.recyclerview.mybooks.BookMyBooksRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.mybooks.BookMyBooksRVOptionsListener;
import com.zlatamigas.readme.databinding.FragmentMybooksBinding;
import com.zlatamigas.readme.util.StringMerger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBooksFragment extends Fragment implements BookMyBooksRVOptionsListener {


    private RecyclerView rvBooks;
    private BookMyBooksRVAdapter rvAdapter;

    private MyBooksViewModel myBooksViewModel;
    private FragmentMybooksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myBooksViewModel = new ViewModelProvider(this).get(MyBooksViewModel.class);

        binding = FragmentMybooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        rvBooks = binding.idFrMyBooksRVBooks;


        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvBooks.setLayoutManager(layoutManager);
        rvBooks.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecVert = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecVert.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2h));
        rvBooks.addItemDecoration(itemDecVert);

        MyBooksFragment currFragment = this;
        ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList = new ArrayList<>();


        Call<List<BookResponseAPIModel>> call = APIProvider.getInstance().getService().getUserPurchased(UserController.getInstance().getToken());
        call.enqueue(new Callback<List<BookResponseAPIModel>>() {
            @Override
            public void onResponse(Call<List<BookResponseAPIModel>> call, Response<List<BookResponseAPIModel>> response) {

                List<BookResponseAPIModel> body = response.body();
                if (body != null) {

                    rvModelBookCommonInfoRVModelList.clear();

                    for (BookResponseAPIModel book : body) {
                        ArrayList<String> authors = new ArrayList<>(book.getAuthors().size());
                        for (AuthorResponseAPIModel author : book.getAuthors()) {
                            authors.add(author.getFullName());
                        }
                        rvModelBookCommonInfoRVModelList.add(new BookCommonInfoRVModel(
                                book.getId(),
                                book.getTitle(),
                                authors,
                                book.getCost(),
                                book.getImageUrl()
                        ));
                    }
                    rvAdapter = new BookMyBooksRVAdapter(requireActivity(), rvModelBookCommonInfoRVModelList, currFragment);
                    rvBooks.setAdapter(rvAdapter);

                    System.out.println("success");
                } else {
                    System.out.println("empty? error?");
                }
            }

            @Override
            public void onFailure(Call<List<BookResponseAPIModel>> call, Throwable t) {
                System.out.println("failure");
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onBookClicked(BookCommonInfoRVModel book, View v) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle args = new Bundle();
        args.putLong("book_id", book.getId());
        navController.navigate(R.id.navigation_book, args);
    }

    // After API 23 the permission request for accessing external storage is changed
    // Before API 23 permission request is asked by the user during installation of app
    // After API 23 permission request is asked at runtime
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;

    @Override
    public void onDownloadClicked(BookCommonInfoRVModel book, View v) {

        Call<BookContentResponseAPIModel> call = APIProvider.getInstance().getService().getBookContent(book.getId(), UserController.getInstance().getToken());
        call.enqueue(new Callback<BookContentResponseAPIModel>() {
            @Override
            public void onResponse(Call<BookContentResponseAPIModel> call, Response<BookContentResponseAPIModel> response) {

                BookContentResponseAPIModel body = response.body();
                if (body != null) {

                    // Requesting Permission to access External Storage
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            EXTERNAL_STORAGE_PERMISSION_CODE);

                    // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
                    // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
                    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


                    byte[] bytes = Base64.decode(body.getContent(), Base64.DEFAULT);

                    try {
                        int i = 1;

                        String[] filenameParts = body.getFileName().split("\\.");

                        String extension = "." + filenameParts[filenameParts.length - 1];

                        String fileNameTitle = filenameParts[0];
                        String fileName = filenameParts[0] + extension;

                        File file = new File(folder, fileName);
                        while (file.exists()){
                            fileName = fileNameTitle + " (" + i + ")" + extension;
                            file = new File(folder, fileName);
                            i++;
                        }

                        try (OutputStream out = new FileOutputStream(file)){
                            out.write(bytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(requireActivity(), "Файл сохранен как: " + fileName, Toast.LENGTH_SHORT).show();

                        System.out.println("success");
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    System.out.println("here we go again...");

                } else {
                    System.out.println("empty? error?");
                }
            }

            @Override
            public void onFailure(Call<BookContentResponseAPIModel> call, Throwable t) {
                System.out.println("failure");
            }
        });
    }

}