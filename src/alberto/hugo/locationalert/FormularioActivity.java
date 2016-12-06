package alberto.hugo.locationalert;

import alberto.hugo.locationalert.dao.NotificacaoDAO;
import alberto.hugo.locationalert.modelo.Notificacao;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.Toast;

public class FormularioActivity extends ActionBarActivity implements
		OnItemSelectedListener {
	private FormularioHelper helper;
	private Notificacao notificacaoParaSerAlterada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario);

		helper = new FormularioHelper(this);

		Gallery gallery = helper.getImage();

		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(FormularioActivity.this,
						"Your selected position = " + position,
						Toast.LENGTH_SHORT).show();

			}
		});

		Intent intent = getIntent();
		notificacaoParaSerAlterada = (Notificacao) intent
				.getSerializableExtra("notificacaoSelecionada");

		helper = new FormularioHelper(this);

		if (notificacaoParaSerAlterada != null) {
			helper.colocaNotificacaoNoFormulario(notificacaoParaSerAlterada);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.formulario, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemClicado = item.getItemId();
		switch (itemClicado) {
		case R.id.salva:
			Notificacao notificacao = helper.pegaNotificacaoDoFormulario();
			NotificacaoDAO dao = new NotificacaoDAO(FormularioActivity.this);

			if (isValidAddress(notificacao.getEndereco().toString()) == true
					&& isValidDescription(notificacao.getDescricao().toString()) == true 
					&& isValidRadius(notificacao.getRaio()) == true) {

				if (notificacaoParaSerAlterada == null) {
					dao.salva(notificacao);
				} else {
					notificacao.setId(notificacaoParaSerAlterada.getId());
					dao.altera(notificacao);
				}

				dao.close();
				finish();

			} else {
				return true;
			}

			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	public boolean isValidAddress(String string) {
		if (string != null && string.length() > 2) {
			return true;
		}
		return false;
	}

	public boolean isValidDescription(String string) {
		if (string != null && string.length() > 0) {
			return true;
		}
		return false;
	}

	public boolean isValidRadius(Integer integer) {
		if (integer != null && integer > 0) {
			return true;
		}
		return false;
	}
}
