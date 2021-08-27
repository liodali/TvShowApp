package dali.hamza.tvshowapp.ui.pages.create_movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.common.isValidMovieForm
import dali.hamza.tvshowapp.ui.MainActivity.Companion.createMovieComposition
import dali.hamza.tvshowapp.ui.common.SpacerHeight
import dali.hamza.tvshowapp.ui.component.LoadingComponent
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun CreateMovieCompose(
    openDateDialog: ((Date) -> Unit, () -> Unit) -> Unit
) {
    val viewModel = createMovieComposition.current.getVM()
    val navController = createMovieComposition.current.getController()
    val movieCreation = viewModel.movieCreation().collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar() {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "")
                    }
                    Text(stringResource(id = R.string.create_movie_page))
                }
            }
        }
    ) {
        Column {
            @OptIn(ExperimentalComposeUiApi::class)
            FormCreateMovie(
                openDateDialog = openDateDialog,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    scope.launch {
                        viewModel.createMovie()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(
                        fraction = 1f
                    )
                    .height(56.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                enabled = viewModel.mutableFlowMovieForm.isValidMovieForm() || (!movieCreation.value.isLoading || movieCreation.value.data == null),
            ) {
                Text(stringResource(id = R.string.createLabel))
            }
        }

    }

    if (movieCreation.value.isLoading) {
        Dialog(
            onDismissRequest = { },

            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ),

            ) {
            Card(
                modifier = Modifier
                    .size(196.dp, 96.dp)
                    .wrapContentSize(align = Alignment.Center)
            ) {
                LoadingComponent()
            }

        }
    }
    if (!movieCreation.value.isLoading && movieCreation.value.data != null) {
        val resourcesCreatedMovie = stringResource(id = R.string.creationMovieSuccess)
        AlertDialog(
            onDismissRequest = {
                viewModel.closeMovieDialogCreation()
                navController.popBackStack()
            },
            buttons = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = {
                            viewModel.closeMovieDialogCreation()
                            navController.popBackStack()
                        },
                        Modifier
                            .padding(5.dp)
                            .wrapContentWidth(align = Alignment.End)
                    ) {
                        Text(text = stringResource(id = android.R.string.ok))
                    }
                }
            },
            text = {
                Text(buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = when (isSystemInDarkTheme()) {
                                true -> Color.White
                                false -> Color.Black
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(movieCreation.value.data?.title ?: "")
                    }
                    withStyle(
                        SpanStyle(
                            color = when (isSystemInDarkTheme()) {
                                true -> Color.White
                                false -> Color.Black
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(resourcesCreatedMovie)
                    }
                })
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true,
            )
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun FormCreateMovie(
    openDateDialog: ((Date) -> Unit, () -> Unit) -> Unit,
    modifier: Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val viewModel = createMovieComposition.current.getVM()
    val scope = rememberCoroutineScope()
    val formSaveable = viewModel.mutableFlowMovieForm
    Column(
        modifier = modifier
    ) {
        SpacerHeight(
            height = 16.dp
        )
        OutlinedTextField(
            value = formSaveable.title,
            onValueChange = {
                viewModel.changeTitleMovie(it)
            },
            label = {
                Text(stringResource(id = R.string.titleLabelForm))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        OutlinedTextField(
            formSaveable.dateRelease,
            {

            },
            label = {
                Text(stringResource(id = R.string.dateRealseLabelEdt))
            },
            trailingIcon = {
                IconButton(onClick = {
                    scope.launch {
                        openDateDialog(
                            { date ->
                                viewModel.changeReleaseDate(date)
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                            {

                            }
                        )
                    }

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_24),
                        contentDescription = "",
                        tint = when (isSystemInDarkTheme()) {
                            true -> Color.White
                            false -> Color.Black
                        }
                    )
                }
            },
            singleLine = true,
            readOnly = false,

            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable {

                }
        )
        OutlinedTextField(
            formSaveable.season,
            {
                viewModel.changeSeason(it)
            }, label = {
                Text(stringResource(id = R.string.seasonLabelEdt))
            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Out)
                    keyboardController!!.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        SpacerHeight(height = 5.dp)

    }
}