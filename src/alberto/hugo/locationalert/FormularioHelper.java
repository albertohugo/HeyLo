package alberto.hugo.locationalert;

import alberto.hugo.locationalert.adapter.GalleryImageAdapter;
import alberto.hugo.locationalert.modelo.Notificacao;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;

public class FormularioHelper {

	private static final String TAG = null;
	private EditText editDescricao;
	private EditText editEndereco;
	private EditText editRaio;
	private Gallery gallery;

	int position = 0;

	ImageView selectedImage;
	private Notificacao notificacao;

	@SuppressWarnings("deprecation")
	public FormularioHelper(FormularioActivity formulario) {

		gallery = (Gallery) formulario.findViewById(R.id.galleryImages);
		gallery.setSpacing(1);
		gallery.setAdapter(new GalleryImageAdapter(formulario));

		editDescricao = (EditText) formulario.findViewById(R.id.descricao);
		editEndereco = (EditText) formulario.findViewById(R.id.endereco);
		editRaio = (EditText) formulario.findViewById(R.id.raio);

		notificacao = new Notificacao();
	}

	public Notificacao pegaNotificacaoDoFormulario() {

		notificacao.setImage((int) gallery.getSelectedItemId());
		Log.d(TAG, "teste: " + gallery.getSelectedItemId());

		notificacao.setDescricao(editDescricao.getText().toString());
		notificacao.setEndereco(editEndereco.getText().toString());

		if (isValidDescription(notificacao.getDescricao().toString()) == false) {
			editDescricao.setError("Invalid Description - 1 character minimum");
		}

		if (isValidAddress(notificacao.getEndereco().toString()) == false) {
			editEndereco.setError("Invalid Address - 3 characters minimum");
		}
		if (isValidRadiusEditable(editRaio.getText()) == false) {
			editRaio.setError("Invalid Radius - 1 number minimum");
			notificacao.setRaio(0);
		} else {
			notificacao
					.setRaio(Integer.parseInt(editRaio.getText().toString()));
		}

		return notificacao;

	}

	public void colocaNotificacaoNoFormulario(
			Notificacao notificacaoParaSerAlterado) {
		notificacao = notificacaoParaSerAlterado;
		gallery.setSelection(notificacaoParaSerAlterado.getImage());
		editDescricao.setText(notificacaoParaSerAlterado.getDescricao());
		editEndereco.setText(notificacaoParaSerAlterado.getEndereco());
		editRaio.setText(notificacaoParaSerAlterado.getRaio().toString());
	}

	public Gallery getImage() {
		return gallery;
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

	public boolean isValidRadiusEditable(Editable radius) {
		if (radius != null && radius.length() > 0) {
			return true;
		}
		return false;
	}

}
