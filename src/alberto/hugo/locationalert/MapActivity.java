package alberto.hugo.locationalert;

import alberto.hugo.locationalert.fragment.MapaFragment;
import alberto.hugo.locationalert.mapa.AtualizadorDePosicao;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapActivity extends ActionBarActivity {

	public AtualizadorDePosicao atualizador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.map_layout);

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		MapaFragment mapaFragment = new MapaFragment();
		transaction.replace(R.id.mapa, mapaFragment);
		transaction.commit();
		atualizador = new AtualizadorDePosicao(this, mapaFragment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemClicado = item.getItemId();
		switch (itemClicado) {
		case R.id.list:
			Intent irParaList = new Intent(this, HeyLoListActivity.class);
			startActivity(irParaList);
			 finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		atualizador.cancelar();
	}
}
