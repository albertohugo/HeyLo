package alberto.hugo.locationalert;

import java.util.List;

import alberto.hugo.locationalert.adapter.ListaNotificacoesAdapter;
import alberto.hugo.locationalert.dao.NotificacaoDAO;
import alberto.hugo.locationalert.modelo.Notificacao;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class HeyLoListActivity extends ActionBarActivity {
	private ListView lista;
	private Notificacao notificacao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_hey_lo);
		
		/*Intent i = new Intent(this, MapActivity.class);  
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(i); */
		
		NotificacaoDAO dao = new NotificacaoDAO(this);
		List<Notificacao> notificacoes = dao.getLista();
		dao.close();

		ListaNotificacoesAdapter adapter = new ListaNotificacoesAdapter(
				notificacoes, this);

		lista = (ListView) findViewById(R.id.lista);

		// Ativa a lista para o menu de longo clique
		registerForContextMenu(lista);
		lista.setAdapter(adapter);

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				Notificacao nofificacaoClicada = (Notificacao) adapter
						.getItemAtPosition(posicao);
				Intent irPararFormularioActivity = new Intent(
						HeyLoListActivity.this, FormularioActivity.class);
				irPararFormularioActivity.putExtra("notificacaoSelecionada",
						nofificacaoClicada);
				startActivity(irPararFormularioActivity);
			}
		});
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {

				notificacao = (Notificacao) adapter.getItemAtPosition(posicao);

				return false;
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.hey_lo, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemClicado = item.getItemId();
		switch (itemClicado) {
		case R.id.novo:
			Intent irParaFormulario = new Intent(this, FormularioActivity.class);
			startActivity(irParaFormulario);			
			break;
		case R.id.mapa:
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager
					.getActiveNetworkInfo();

			if (activeNetworkInfo != null
					&& activeNetworkInfo.isConnectedOrConnecting()) {
				Intent irParaMapa = new Intent(this, MapActivity.class);
				startActivity(irParaMapa);
				
			} else {
				CallNetworkSetting();
			}

			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		MenuItem deletar = menu.add("Deletar");
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				NotificacaoDAO dao = new NotificacaoDAO(HeyLoListActivity.this);
				dao.deletar(notificacao);
				carregaLista();
				dao.close();
				return false;
			}
		});

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		carregaLista();

	}

	private void carregaLista() {
		NotificacaoDAO dao = new NotificacaoDAO(this);
		List<Notificacao> notificacao = dao.getLista();
		dao.close();
		ListaNotificacoesAdapter adapter = new ListaNotificacoesAdapter(
				notificacao, this);

		lista.setAdapter(adapter);
	}

	private void CallNetworkSetting() {
		final Context ctx = this;
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setCancelable(true);
		builder.setMessage("This application requires a working data connection.");
		builder.setTitle("No Connection");
		builder.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						ctx.startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				return;
			}
		});

		builder.show();
	}

	

}
