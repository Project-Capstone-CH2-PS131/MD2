package com.example.capstone.ui.pages

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import coil.compose.AsyncImage
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.rememberScaffoldState
import coil.compose.rememberImagePainter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.compose.rememberNavController
import com.example.capstone.R
import com.example.capstone.data.utils.getImageUri
import com.example.capstone.data.utils.reduceFileImage
import com.example.capstone.data.utils.uriToFile
import com.example.capstone.ui.component.BottomBar
import com.example.capstone.ui.component.JetButton
import com.example.capstone.ui.component.JetTextField
import com.example.capstone.ui.component.JetTopBar
import com.example.capstone.ui.theme.BluePrimary
import com.example.capstone.ui.theme.CapstoneTheme
import com.example.capstone.ui.theme.CyanPrimary
import java.io.File
import java.util.Date
import java.util.Objects


class UploadPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),

                ) {
//                    WelcomePages()
                    UploadPages()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun UploadPages(
//        navigateToResult: () -> Unit,
    ) {
        Scaffold(
            topBar = { JetTopBar(context = LocalContext.current) }
        ) {
            UploadPagesContent(
                navigateToResult = {  }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UploadPagesContent(
        modifier: Modifier = Modifier,
        navigateToResult: () -> Unit,
        context: Context = LocalContext.current,
//        viewModel: ScanViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
//            factory = PredictModelFactory.getInstance(context)
//        ),
    ) {
//        val goBack: () -> Unit
        var description by remember { mutableStateOf("") }

        var showDialog by remember {
            mutableStateOf(false)
        }

        var showLoading by remember {
            mutableStateOf(false)
        }


        var currentImageUri: Uri? = null

        var capturedImageUri by remember {
            mutableStateOf<Uri>(Uri.EMPTY)
        }

        val launcherCamera =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
                capturedImageUri = currentImageUri!!
            }

        val launcherGallery = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                capturedImageUri = currentImageUri!!
            } else {
                Log.d("Photo Picker", "No Media Selected")
            }

        }


        fun startGallery() {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        fun startCamera() {
            currentImageUri = context.getImageUri(context)
            launcherCamera.launch(currentImageUri)
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                startCamera()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = Color.White,
                    )
            ) {
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(20.dp)
//                )
                /*
                Place Holder For Image After user taking a photo or scan
             */
                Box(modifier = Modifier) {
                    Image(
                        painter = painterResource(id = R.drawable.background2profile),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .matchParentSize()
                            .fillMaxWidth()
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .padding(top = 140.dp,bottom = 16.dp, start = 16.dp, end = 16.dp),
                        elevation = 10.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .fillMaxHeight()
                                .padding(start = 20.dp, end = 20.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (capturedImageUri.path?.isNotEmpty() == true) {
                                AsyncImage(
                                    model = capturedImageUri,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentDescription = "LogoApp",
                                    contentScale = ContentScale.Fit
                                )
                            } else if (capturedImageUri.path?.isEmpty() == true) {
                                Image(
                                    painter = painterResource(id = R.drawable.photoupload),
                                    contentDescription = "",
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (showLoading) {
                            val infiniteTransition = rememberInfiniteTransition()

                            val boxSize = 420.dp
                            val animationDuration = 5000

                            val heightAnimation by infiniteTransition.animateValue(
                                initialValue = 0.dp,
                                targetValue = boxSize,
                                typeConverter = Dp.VectorConverter,
                                animationSpec = infiniteRepeatable(
                                    animation = keyframes {
                                        durationMillis = animationDuration
                                        0.dp at 0 // ms
                                        boxSize at animationDuration / 2
                                        0.dp at animationDuration using FastOutSlowInEasing
                                    }
                                    // Use the default RepeatMode.Restart to start from 0.dp after each iteration
                                )
                            )
                            Spacer(modifier = Modifier.height(heightAnimation))
                            Divider(
                                thickness = 4.dp, color = Color.Black,
                                modifier = Modifier.fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            )
                        }
                    }

                }


                if (showDialog) {
                    androidx.compose.material3.AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                        },
                        title = {
                            androidx.compose.material3.Text(text = "Scan Berhasil")
                        },
                        icon = {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "checkCircle"
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
//                                    navigateToResult()
                                },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    MaterialTheme.colorScheme.primary
                                )
                            ) {
                                androidx.compose.material3.Text(
                                    text = "Yes",
                                    color = Color.Black
                                )
                            }
                        }
                    )
                }

                /*
                Button Gallery And Camera
             */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, top = 20.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    JetButton(
                        onClick = { startGallery() },
                        color = BluePrimary,
                        enabled = true,
                        label = "File",
                        modifier = Modifier.size(width = 160.dp, height = 50.dp)
                    )
//                    {
//                        androidx.compose.material3.Icon(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.filedrive),
//                            contentDescription = "Gallery",
//                            tint = Color.White
//                        )
//                    }

                    JetButton(
                        onClick =
                        {
                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                )
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                startCamera()
                            } else {
                                // Request a permission
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                        color = BluePrimary,
                        enabled = true,
                        label = "CAMERA",
                        modifier = Modifier.size(width = 200.dp, height = 50.dp)
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
//                            val imageFile =
//                                context.uriToFile(capturedImageUri, context).reduceFileImage()
////                            viewModel.uploadImage(imageFile)
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .padding(top = 50.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        if (showLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.Gray
                            )
                        } else {
                            androidx.compose.material3.Text(
                                text = "Upload",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )
                        }
                    }
                }

            }
        }
    }
}

//    @Composable
//    @Preview(showBackground = true)
//    fun UploadPagePreview() {
//        CapstoneTheme {
//            UploadPage()
//        }
//    }
//}
