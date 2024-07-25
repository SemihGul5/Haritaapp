import android.content.Context
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.model.ParkItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class CustomClusterRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<ParkItem>
) : DefaultClusterRenderer<ParkItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: ParkItem, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        // İstediğiniz marker ikonunu burada ayarlayın
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_directions_bus_24_black))
    }

    override fun onBeforeClusterRendered(cluster: Cluster<ParkItem>, markerOptions: MarkerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions)
        // Kümelenmiş marker için ikon ayarlama
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.custom_cluster_icon))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<ParkItem>): Boolean {
        // Küme olarak gösterilecek minimum marker sayısını ayarlayın
        return cluster.size > 1
    }
}
