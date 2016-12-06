package alberto.hugo.locationalert.fragment;

import java.util.List;

import alberto.hugo.locationalert.R;
import alberto.hugo.locationalert.dao.NotificacaoDAO;
import alberto.hugo.locationalert.mapa.Localizador;
import alberto.hugo.locationalert.modelo.Notificacao;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends SupportMapFragment {
	private static final String TAG = null;

	private Integer[] mImageIds = { R.drawable.ic_home, R.drawable.ic_friends,
			R.drawable.ic_love, R.drawable.ic_ok, R.drawable.ic_nok,
			R.drawable.ic_mark, R.drawable.ic_plane, R.drawable.ic_beach,
			R.drawable.ic_stadium };

	private GoogleMap map;
	public int countNotification=0;
	public CircleOptions circleOptions;	
	public CircleOptions[] circleOptionList = new CircleOptions[999];	
	public List<Notificacao> notificacoes;
	
		@Override
		public void onResume() {
			super.onResume();

		showNotificationOnMap();

	}

		private void showNotificationOnMap() {
			
			FragmentActivity context = getActivity();
			
			// LatLng local = new LatLng(-3.717, -38.54);//Zoom no mapa de Fortaleza
			// Localizador(context).getCoordenada("Jovita Feitosa 2305 Parquelandia");	

			NotificacaoDAO dao = new NotificacaoDAO(context);
			notificacoes = dao.getLista();

			for (Notificacao notificacao : notificacoes) {
				Log.d(TAG, "Desc:" + notificacao.getDescricao() + " End: "
						+ notificacao.getEndereco() + notificacao.getRaio());

				map = getMap();
				LatLng localNotificacao = new Localizador(context)
						.getCoordenada(notificacao.getEndereco());
				MarkerOptions options = new MarkerOptions()
						.title(notificacao.getDescricao())
						.position(localNotificacao)
						.icon(BitmapDescriptorFactory
								.fromResource(mImageIds[notificacao.getImage()]));
				map.addMarker(options);

				
				
				setCircleOptions(new CircleOptions().center(
						localNotificacao).radius(notificacao.getRaio()));
				getCircleOptions().strokeColor(Color.GRAY);

				// Fill color of the circle
				// 0x represents, this is an hexadecimal code
				// 55 represents percentage of transparency. For 100% transparency,
				// specify 00.
				// For 0% transparency ( ie, opaque ) , specify ff
				// The remaining 6 characters(4682B4) specify the fill color
				getCircleOptions().fillColor(0x554682B4);
				getCircleOptions().strokeWidth(0);
				map.addCircle(getCircleOptions());
				
				circleOptionList[countNotification]=getCircleOptions();
				countNotification++;
			}
			
			
			dao.close();
		}

	public void centralizaLocal(LatLng local) {

		GoogleMap map = getMap();
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(local, 15);
		map.animateCamera(update);

	}

	public CircleOptions getCircleOptions() {
		return circleOptions;
	}

	public void setCircleOptions(CircleOptions circleOptions) {
		
		this.circleOptions = circleOptions;
	}

}
