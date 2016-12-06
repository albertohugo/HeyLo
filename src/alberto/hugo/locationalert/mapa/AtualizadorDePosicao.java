package alberto.hugo.locationalert.mapa;

import alberto.hugo.locationalert.HeyLoListActivity;
import alberto.hugo.locationalert.R;
import alberto.hugo.locationalert.fragment.MapaFragment;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AtualizadorDePosicao implements LocationListener {

	private static final String TAG = "ALB";
	public LocationManager locationManager;
	private MapaFragment mapa;
	private Marker currentLocation = null;
	private Marker validationCicle = null;
	private LatLng local = null;

	public AtualizadorDePosicao(Activity activity, MapaFragment mapa) {
		Log.d(TAG, "Location OK: " + local);
		this.mapa = mapa;
		locationManager = (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);

		String provider = LocationManager.GPS_PROVIDER;
		long tempoMinimo = 20000;// ms
		float distanciaMinima = 20;// m

		locationManager.requestLocationUpdates(provider, tempoMinimo,
				distanciaMinima, this);
		
	}

	public void cancelar() {
		locationManager.removeUpdates(this);

	}

	@Override
	public void onLocationChanged(Location novaLocalizacao) {	
		Log.d(TAG, "currentLocation: " + currentLocation);

		double latitude = novaLocalizacao.getLatitude();
		double longitude = novaLocalizacao.getLongitude();
		local = new LatLng(latitude, longitude);
		mapa.centralizaLocal(local);
		
		if (currentLocation != null && validationCicle != null) {
			currentLocation.remove();
			markerCurrentLocation();
			validationCicle.remove();
			markerValidationIntoCircle();
		} else {
			markerCurrentLocation();
			markerValidationIntoCircle();
		}

	}

	private void markerCurrentLocation() {

		currentLocation = mapa.getMap().addMarker(
				new MarkerOptions()
						.position(local)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_current_location))
						.title("Current Location").alpha(0.7f));

	}

	private void markerValidationIntoCircle() {
		
		float[] distance = new float[2];	
		
		int count=0;		
		while(mapa.countNotification>count){	
		
		mapa.circleOptions = mapa.getCircleOptions();
		Location.distanceBetween(currentLocation.getPosition().latitude,
				currentLocation.getPosition().longitude,
				mapa.circleOptionList[count].getCenter().latitude,
				mapa.circleOptionList[count].getCenter().longitude, distance);		
		
		Log.d(TAG, "Distance Between circle " + count + ": " +  distance[0]);			
		
		if (distance[0] > mapa.circleOptionList[count].getRadius()) {			
			Log.d(TAG, "[Out] Radius of circle  " + count + ": "+  mapa.circleOptionList[count].getRadius());			
			if(validationCicle != null){
				validationCicle.remove();
				markerOutRadius();					
			}else
			{
				markerOutRadius();				
			}			
		} else {
			Log.d(TAG, "[In] Radius of circle  " + count + ": "+  mapa.circleOptionList[count].getRadius());			
			
			if(validationCicle != null){
				validationCicle.remove();
				markerInRadius();	
				
				return;
			}else
			{
				markerInRadius();	
				return;
			}
		}count++;}
		
	}

	private void markerInRadius() {
		validationCicle = mapa.getMap().addMarker(
				new MarkerOptions()
						.position(local)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_radius_in))
						.title("Current Location").alpha(0.7f));
	}

	private void markerOutRadius() {
		validationCicle = mapa.getMap().addMarker(
				new MarkerOptions()
						.position(local)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_radius_out))
						.title("Current Location").alpha(0.7f));
	}

	@Override
	public void onProviderDisabled(String provider) {
		// GPS WIFI
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public static LatLng fromLocationToLatLng(Location location) {
		return new LatLng(location.getLatitude(), location.getLongitude());

	}

}
