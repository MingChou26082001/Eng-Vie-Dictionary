package com.example.dictionary;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import android.widget.Toast;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    Trie head = new Trie();
//    Set<String> words;
//    List<String> suggestwords;
//    String item ="";
//    String[] items0 ;
//    HashMap<String, String> tdav = new HashMap<>();
//    String filename ="";
//    String filepath ="";
//    String fileContent;
//    FileReader fr = null;
//    FileWriter fw = null;
//    File myExternalFile;
//    int i =0;
//    String[] items;
//    ArrayAdapter<String> arrayAdapter, arrayAdapter1;
    AutoCompleteTextView autoCompleteTextView;
    String itemEng;
    EditText textEng;
    Button button;
    ArrayAdapter dictionaryArrayAdapter;
    ListView lv_dictionaryList;
    DatabaseHandler databaseHandler;

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoCompleteTextView = findViewById(R.id.editDich);
        autoCompleteTextView.setThreshold(1);
        lv_dictionaryList = findViewById(R.id.lv_dictionaryList);
        button = findViewById(R.id.button1);

//        TextView textVn = (TextView) findViewById(R.id.printDich);
//        databaseHandler = new DatabaseHandler(MainActivity.this);
//        extracted(databaseHandler);


//        lv_dictionaryList.setOnItemClickListener((adapterView, view, position, l) -> {
//            databaseHandler = new DatabaseHandler(MainActivity.this);
//            List<DictionaryModel> history = databaseHandler.getHistory();
//
//            DictionaryModel clickedWords = (DictionaryModel) adapterView.getItemAtPosition(i);
//            databaseHandler.deleteOne(clickedWords);
//
//            dictionaryArrayAdapter = new ArrayAdapter<DictionaryModel>(MainActivity.this, android.R.layout.simple_list_item_1, history);
//            lv_dictionaryList.setAdapter(dictionaryArrayAdapter);
//            Toast.makeText(MainActivity.this, "Deleted " + clickedWords.toString(), Toast.LENGTH_SHORT).show();
//
//            String selectedFromList = lv_dictionaryList.getItemAtPosition(position).toString();
//            textEng.setText(selectedFromList);
//
//
//        });


//        filename = "hehe.txt";
//        filepath = "MyFileDir";
//
//        myExternalFile = new File(getExternalFilesDir("MyFileDir"), "hehe.txt");
//        try{
//            fr = new FileReader(myExternalFile);
//            BufferedReader br = new BufferedReader(fr);
//            DictionaryModel dictionary = null;
//            String line;
//            while((line = br.readLine()) != null){
////                  TEST
////                    sb.append(line).append('\n');
////                    line = br.readLine();
//                String[] parts = line.split(" : ", 2);
//                if (parts.length >= 2)
//                {
//                    String value = parts[0];
//                    String key = parts[1];
//
////                    head.insert(key);
////                    tdav.put(key, value);
//
//                    dictionary = new DictionaryModel(key, value);
//                    databaseHandler = new DatabaseHandler(MainActivity.this);
//                    databaseHandler.addOneDictionary(dictionary);
//                }
//            }
//            fr.close();
//            br.close();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        words = head.autoComplete(item);
//        int n=words.size();//Trie auto complete words
//        items = Arrays.copyOf(words.toArray(), n, String[].class); //copy from set to array
//        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1,items);
//        arrayAdapter1 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1,databaseHandler.getHistory());
//

        databaseHandler = new DatabaseHandler(MainActivity.this);
        dictionaryArrayAdapter = new ArrayAdapter<>(this, R.layout.history_item, R.id.textItem, databaseHandler.getHistory());


        lv_dictionaryList.setAdapter(dictionaryArrayAdapter);

        textEng = findViewById(R.id.editDich);
        itemEng = textEng.getText().toString().trim();


//        autoCompleteTextView.setAdapter(arrayAdapter);
//        autoCompleteTextView.setOnDismissListener(() -> {
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0 );
//        });
       //autoCompleteTextView.showDropDown();

        textEng.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(textEng.length() <1){

                    dictionaryArrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.history_item, R.id.textItem, databaseHandler.getHistory());
                    lv_dictionaryList.setAdapter(dictionaryArrayAdapter);
                    dictionaryArrayAdapter.notifyDataSetChanged();
                    TextView textVn = findViewById(R.id.printDich);
                    textVn.setText("");
                    lv_dictionaryList.setVisibility(View.VISIBLE);
                }

                else {
                    itemEng = textEng.getText().toString().trim();
//                  databaseHandler = new DatabaseHandler(MainActivity.this);
                    dictionaryArrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.dictionary_item, R.id.textItem, databaseHandler.getSuggestion(itemEng));
                    lv_dictionaryList.setAdapter(dictionaryArrayAdapter);

                    dictionaryArrayAdapter.notifyDataSetChanged();
                    lv_dictionaryList.setVisibility(View.VISIBLE);
                }
            }
        });


        lv_dictionaryList.setOnItemClickListener((parent, view, position, id) -> {
            itemEng = lv_dictionaryList.getItemAtPosition(position).toString();
            textEng.setText(itemEng);
            HistoryModel dictionary = new HistoryModel(itemEng);
            TextView textVn = findViewById(R.id.printDich);
//                textVn.setText("Không có từ này");
//                itemEng = textEng.getText().toString().trim();
            textVn.setText(databaseHandler.getMeanings(itemEng));
            databaseHandler.addOneHistory(dictionary);
            lv_dictionaryList.setVisibility(View.INVISIBLE);

        });

//        button.setOnClickListener(view -> {
//                    databaseHandler = new DatabaseHandler(MainActivity.this);
//                    databaseHandler.deleteALLHistory();
//                    dictionaryArrayAdapter.notifyDataSetChanged();
//                }
//        );
        //arrayAdapter.clear();



//        int i=0
//        for (String x : words){
//            items[i++] =x;
//            if(i==4) break;
//        }

//        words.stream().forEach(x -> {
//
//            items[i] = x;
//        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

                case R.id.clearHistory:
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Bạn có chắc muốn xoá lịch sử tìm kiếm không?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseHandler = new DatabaseHandler(MainActivity.this);
                            databaseHandler.deleteALLHistory();

                            dictionaryArrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.history_item, R.id.textItem, databaseHandler.getHistory());
                            lv_dictionaryList.setAdapter(dictionaryArrayAdapter);
                            dictionaryArrayAdapter.notifyDataSetChanged();
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();

//                databaseHandler = new DatabaseHandler(MainActivity.this);
//                databaseHandler.deleteALLHistory();
//                dictionaryArrayAdapter.notifyDataSetChanged();
                return true;

            case R.id.about:
                AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog1.setTitle("Giới thiệu ứng dụng");
                alertDialog1.setMessage("Ứng dụng từ điển Anh-Việt của sinh viên Dương Minh Châu được làm trong môn học PROJECT 2");
                alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "Beta 0.1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog1.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @SuppressLint("SetTextI18n")
    @Override
        public void onClick (View view) {

//        if(view.getId() == buttonDich.getId()) {
//
//            filename = "test.txt";
//            filepath = "MyFileDir";
//            File myExternalFile = new File(getExternalFilesDir(filepath), filename);
//
//            textEng = (EditText) findViewById(R.id.editDich);
//            String item1 = textEng.getText().toString().trim();
//
//            if (tdav.containsKey(item1)) {
//                TextView textVn = (TextView) findViewById(R.id.printDich);
//                textVn.setText(tdav.get(item1));
//                HistoryModel dictionary = null;
//                try {
//                    dictionary = new HistoryModel(textEng.getText().toString());
//                    Toast.makeText(MainActivity.this   , "Added to history", Toast.LENGTH_SHORT).show();
//                }
//                catch(Exception e){
//                    Toast.makeText(MainActivity.this   , "Not added to history", Toast.LENGTH_SHORT).show();
//                }
//
//                DatabaseHandler databaseHandler = new DatabaseHandler(MainActivity.this);
//                boolean success= databaseHandler.addOneHistory(dictionary);
//                Toast.makeText(MainActivity.this   , "Success: " + success, Toast.LENGTH_SHORT).show();
//
//
////                String itemRS = item2 + " : " + item1 + "\n";
////                if(myExternalFile.exists()) {
////                    if (!item1.equals("") || !item2.equals("")) {
////
////
////                        try {
////                            fw = new FileWriter(myExternalFile,true);
////                            BufferedWriter bufferedWriter = new BufferedWriter(fw);
////                            fw.write(itemRS);
////                            head0.insert(item1);
////                            fw.close();
////                            bufferedWriter.close();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                        Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
////                    }
////                }
////                else
////                    Toast.makeText(MainActivity.this, "not saved", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                TextView textVn = (TextView) findViewById(R.id.printDich);
//                textVn.setText("Không có từ: "+ item1);
//            }
//// truy cap 2 mang, uu tien tu da co truoc
//
//
////            try{
////
////                fr = new FileReader(myExternalFile);
////                BufferedReader br = new BufferedReader(fr);
////                String line = br.readLine();
////
////                while(line != null){
//////                  TEST
////
//////                    sb.append(line).append('\n');
//////                    line = br.readLine();
////
////
////                    String[] parts = line.split(":", 2);
////                    if (parts.length >= 2)
////                    {
////                        String key = parts[0];
////                        String value = parts[1];
////                        tdav.put(key, value);
////                    } else {
////                        System.out.println("ignoring line: " + line);
////                    }
////                    line = br.readLine();
////                }
////            }
////            catch (FileNotFoundException e){
////                e.printStackTrace();
////
////            }
////
////            catch (IOException e){
////                e.printStackTrace();
////            }
//
//
////              TEST
//
////                fileContent = "File contents\n" + sb.toString();
////                TextView textVN = (TextView) findViewById(R.id.printDich);
////                textVN.setText(fileContent);
//
//
//
//
////
////        tdav.put("this", "day");
////        tdav.put("is", "la");
////        tdav.put("a", "mot");
////
////        tdav.put("test", "thu nghiem");
//
//
//
//
///*
//            // Day la code de tao ra file
//                fileContent = textEng.getText().toString().trim();
//                if(!fileContent.equals("")){
//                    File externalFile = new File(getExternalFilesDir(filepath), filename);
//                    FileOutputStream fos = null;
//                    try {
//                        fos = new FileOutputStream(externalFile);
//                        fos.write(fileContent.getBytes());
//                    }
//                    catch (FileNotFoundException e){
//                        e.printStackTrace();
//                    }
//                    catch (IOException e){
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(this, "File has been made", Toast.LENGTH_SHORT).show();
//                }
//                else
//                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
//*/
//            }
//
//        }
    }
}
