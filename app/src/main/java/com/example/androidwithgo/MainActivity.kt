package com.example.androidwithgo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidwithgo.ui.theme.AndroidWithGoTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*

import simple.Simple

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidWithGoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home(){

//    var pk_ser = ""
    var pk_ser by remember{ mutableStateOf("") } // crash if presented
    //var pk_ser = ""
    var sk_ser by remember{ mutableStateOf("") } // crash
    //var sk_ser = ""
    var ctxt_ser by remember{ mutableStateOf("") } // crash
    //var ctxt_ser = ""
    //var dec_str = ""
    var dec_str by remember{ mutableStateOf("") } // crash

    var isLoading by remember { mutableStateOf(false) }

    suspend fun doTask1(): MutableList<String>{
        var tmp = Simple.keyGen()

        var res: MutableList<String> = mutableListOf()

        var tmp_pk = Simple.keyPairs_to_public_encode(tmp)
        var tmp_sk = Simple.keyPairs_to_secret_encode(tmp)

        res.add(tmp_pk)
        res.add(tmp_sk)

        return res
    }


    LaunchedEffect(key1=Unit){
    }

    Column {

        Text("DEC_STR",
            modifier = Modifier.padding(all = 8.dp)
        )
        Text(dec_str,
                modifier = Modifier.padding(all = 8.dp)
        )
        Button(
            onClick = {
                isLoading = true
                var tmp = Simple.keyGen()
                pk_ser = Simple.keyPairs_to_public_encode(tmp)
                sk_ser = Simple.keyPairs_to_secret_encode(tmp)
                Log.d("DEBUG", "KEYGEN========================")
                Log.d("DEBUG_PK_STR", pk_ser)
                Log.d("DEBUG_SK_STR", sk_ser)
                isLoading = false
//                if (!isLoading) {
//                    isLoading = true
//                    CoroutineScope(Dispatchers.IO).launch {
//                        try {
//
////                            kotlinx.coroutines.delay(5000) // Replace with your actual task
//                            //var tmp = Simple.keyGen()
//                            var tmp = Simple.keyGen()
//                            var tmp_pk = Simple.keyPairs_to_public_encode(tmp)
//                            var tmp_sk = Simple.keyPairs_to_secret_encode(tmp)
//
//                            // Re-enable the button on the main thread
//                            withContext(Dispatchers.Main) {
//                                isLoading = false
//                                pk_ser = tmp_pk
//                                sk_ser = tmp_sk
//
//                            }
//                        } catch (e: Exception) {
//                            println(e)
//                        }
//                    }
//                }
            },
            enabled = !isLoading
        ) {
            if (isLoading) {
                // Display a loading indicator or text
                CircularProgressIndicator()
            } else {
                // Display the button text
                Text("KeyGen")
            }
        }

        Button(
        onClick = {
            isLoading = true
            ctxt_ser = Simple.encryptEncode(pk_ser)
            isLoading = false
            Log.d("DEBUG", "ENCRYPT========================")
            Log.d("DEBUG_PK_STR", pk_ser)
            Log.d("DEBUG_SK_STR", sk_ser)
            Log.d("DEBUG_CTXT_STR", ctxt_ser)
//                if (!isLoading) {
//                    isLoading = true
//                    CoroutineScope(Dispatchers.IO).launch {
//                        try {
//                            var tmp = Simple.encryptEncode(pk_ser)
//
//                            // Re-enable the button on the main thread
//                            withContext(Dispatchers.Main) {
//                                isLoading = false
//                                ctxt_ser = tmp
//
//                            }
//                        } catch (e: Exception) {
//                            println(e)
//                        }
//                    }
//                }
            },
            enabled = (!isLoading && pk_ser != "")
        ) {
            if (isLoading) {
                // Display a loading indicator or text
                CircularProgressIndicator()
            } else {
                // Display the button text
                Text("Encrypt")
            }
        }

        Button(
            onClick = {
                Log.d("DEBUG", "DECRYPT========================")
                isLoading = true
                dec_str = Simple.decodeDecrypt(ctxt_ser, sk_ser)
                isLoading = false
                Log.d("DEBUG_DEC_STR", dec_str)
            },
            enabled = (!isLoading && ctxt_ser != "" && sk_ser != "")
            //enabled = !isLoading
        ) {
            if (isLoading) {
                // Display a loading indicator or text
                CircularProgressIndicator()
            } else {
                // Display the button text
                Text("Decrypt")
            }
        }

    }
}
