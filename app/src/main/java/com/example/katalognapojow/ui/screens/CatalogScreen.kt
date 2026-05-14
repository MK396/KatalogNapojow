import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.katalognapojow.Screen
import com.example.katalognapojow.ui.theme.Orange


@Composable
fun CatalogScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    // Sprawdzamy, czy telefon jest w trybie poziomym
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Wyśrodkowanie w pionie
    ) {
        if (!isLandscape) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (isLandscape) {
            // W trybie LANDSCAPE wyświetlamy wiersz (Row)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Używamy Modifier.weight(1f), aby przyciski dzieliły przestrzeń po równo
                CategoryButton(
                    text = "Napoje gazowane",
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(Screen.SparklingDrinks.route) }
                )
                CategoryButton(
                    text = "Napoje niegazowane",
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(Screen.StillDrinks.route) }
                )
                CategoryButton(
                    text = "Napoje gorące",
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(Screen.HotDrinks.route) }
                )
            }
        } else {
            // W trybie PORTRAIT wyświetlamy standardową kolumnę (Column)
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CategoryButton(
                    text = "Napoje gazowane",
                    onClick = { navController.navigate(Screen.SparklingDrinks.route) }
                )
                CategoryButton(
                    text = "Napoje niegazowane",
                    onClick = { navController.navigate(Screen.StillDrinks.route) }
                )
                CategoryButton(
                    text = "Napoje gorące",
                    onClick = { navController.navigate(Screen.HotDrinks.route) }
                )
            }
        }
    }
}

@Composable
fun CategoryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Orange,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(40.dp)
    ) {
        Text(text = text, fontSize = 18.sp, textAlign = TextAlign.Center)
    }
}