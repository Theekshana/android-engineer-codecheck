package jp.co.yumemi.android.code_check.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [NetworkUtils].
 */
@Suppress("DEPRECATION")
class NetworkUtilsTest {
    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @Mock
    private lateinit var networkCapabilities: NetworkCapabilities

    @Mock
    private lateinit var network: Network

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
    }

    /**
     * Test case to check if [NetworkUtils.hasInternetConnection] correctly detects an active
     * Wi-Fi network.
     */
    @Test
    fun testHasInternetConnection_WithWifi() {
        // Simulate a Wi-Fi connection
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(null)  // No active network
        val hasInternetConnection = NetworkUtils.hasInternetConnection(context)
        assert(!hasInternetConnection)

        Mockito.`when`(connectivityManager.activeNetwork)
            .thenReturn(network)  // There's an active network
        Mockito.`when`(connectivityManager.getNetworkCapabilities(network))
            .thenReturn(networkCapabilities)
        Mockito.`when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(true)

        val hasInternetConnectionWithWifi = NetworkUtils.hasInternetConnection(context)
        assert(hasInternetConnectionWithWifi)
    }

    /**
     * Test case to check if [NetworkUtils.hasInternetConnection] correctly detects an active
     * cellular network.
     */
    @Test
    fun testHasInternetConnection_WithCellular() {
        // Simulate a cellular connection
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(network)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(network))
            .thenReturn(networkCapabilities)
        Mockito.`when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            .thenReturn(true)

        val hasInternetConnectionWithCellular = NetworkUtils.hasInternetConnection(context)
        assert(hasInternetConnectionWithCellular)
    }

    /**
     * Test case to check if [NetworkUtils.hasInternetConnection] correctly detects an active
     * Ethernet network.
     */
    @Test
    fun testHasInternetConnection_WithEthernet() {
        // Simulate an Ethernet connection
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(network)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(network))
            .thenReturn(networkCapabilities)
        Mockito.`when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
            .thenReturn(true)

        val hasInternetConnectionWithEthernet = NetworkUtils.hasInternetConnection(context)
        assert(hasInternetConnectionWithEthernet)
    }

    /**
     * Test case to check if [NetworkUtils.hasInternetConnection] correctly detects no active network.
     */
    @Test
    fun testHasInternetConnection_NoConnection() {
        // Simulate no active network
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(null)

        val hasInternetConnection = NetworkUtils.hasInternetConnection(context)
        assert(!hasInternetConnection)
    }
}