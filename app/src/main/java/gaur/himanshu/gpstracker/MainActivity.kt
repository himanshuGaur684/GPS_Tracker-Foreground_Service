package gaur.himanshu.gpstracker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import gaur.himanshu.gpstracker.service.LocationService
import gaur.himanshu.gpstracker.ui.theme.GPSTrackerTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GPSTrackerTheme {
                Surface(
                    modifier = Modifier
                        .safeContentPadding()
                        .fillMaxSize()
                ) {

                    val permission = rememberMultiplePermissionsState(permissions =
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                        listOf(
                            android.Manifest.permission.POST_NOTIFICATIONS,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        )
                    }else{
                        listOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        )
                    })

                    LaunchedEffect(Unit) {
                        permission.launchMultiplePermissionRequest()
                    }

                    when{
                        permission.allPermissionsGranted->{
                            Column(
                                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(onClick = {
                                    val intent = Intent(this@MainActivity, LocationService::class.java)
                                    startService(intent)
                                }) {
                                    Text(text = "Start Service")
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(onClick = {
                                    val intent = Intent(this@MainActivity, LocationService::class.java)
                                    stopService(intent)
                                }) {
                                    Text(text = "Stop Service")
                                }
                            }
                        }

                        permission.shouldShowRationale->{
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                Button(onClick = { permission.launchMultiplePermissionRequest() }) {
                                    Text(text = "Give Permissions")
                                }
                            }
                        }
                    }



                }
            }
        }
    }
}

