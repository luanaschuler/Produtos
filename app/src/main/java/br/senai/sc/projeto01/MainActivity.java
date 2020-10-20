package br.senai.sc.projeto01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.senai.sc.projeto01.modelo.Produto;

public class MainActivity extends AppCompatActivity {

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;

    private final int REQUEST_CODE_NOVO_PRODUTO = 1;
    private final int RESULT_CODE_NOVO_PRODUTO = 10;
    private final int REQUEST_CODE_EDITAR_PRODUTO = 2;
    private final int RESULT_CODE_PRODUTO_EDITADO = 11;
    private final int REQUEST_CODE_EXCLUIR_PRODUTO = 3;
    private final int RESULT_CODE_PRODUTO_EXCLUIDO = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Lista de Produtos");

        listViewProdutos = findViewById(R.id.lisView_produtos);
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        adapterProdutos = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                produtos);
        listViewProdutos.setAdapter(adapterProdutos);

        definirOnClickListenerListView();
        definirOnLongClickListener();
    }

   private void definirOnLongClickListener(){
        listViewProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Produto produtoClicado = adapterProdutos.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Excluir Item")
                .setMessage("Deseja excluir este item?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterProdutos.remove(produtoClicado);
                        adapterProdutos.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Produto Excluído com Sucesso", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Não", null).show();
            return true;
            }
        });
   }


    private void definirOnClickListenerListView() {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoClicado = adapterProdutos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
                intent.putExtra("produtoEdicao", produtoClicado);
                startActivityForResult(intent, REQUEST_CODE_EDITAR_PRODUTO);
            }
        });
    }

    public void onClickNovoProduto(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NOVO_PRODUTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_NOVO_PRODUTO && resultCode == RESULT_CODE_NOVO_PRODUTO) {
            Produto produto = (Produto) data.getExtras().getSerializable("novoProduto");
            produto.setId(++id);
            this.adapterProdutos.add(produto);
            Toast.makeText(MainActivity.this, "Produto salvo", Toast.LENGTH_LONG).show();
        } else if(requestCode == REQUEST_CODE_EDITAR_PRODUTO && resultCode == RESULT_CODE_PRODUTO_EDITADO) {
            Produto produtoEditado = (Produto) data.getExtras().getSerializable("produtoEditado");
            for (int i=0; i < adapterProdutos.getCount(); i++) {
                Produto produto = adapterProdutos.getItem(i);
                if (produto.getId() == produtoEditado.getId()) {
                    adapterProdutos.remove(produto);
                    adapterProdutos.insert(produtoEditado, i);
                    break;
                }
            }
            Toast.makeText(MainActivity.this, "Produto Editado", Toast.LENGTH_LONG).show();
        } else if (requestCode == REQUEST_CODE_EDITAR_PRODUTO && resultCode == RESULT_CODE_PRODUTO_EXCLUIDO) {
            Produto produtoExcluido = (Produto) data.getExtras().getSerializable("produtoExcluido");
            for (int i=0; i < adapterProdutos.getCount(); i++) {
                Produto produto = adapterProdutos.getItem(i);
                if (produto.getId() == produtoExcluido.getId()) {
                    adapterProdutos.remove(produto);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}