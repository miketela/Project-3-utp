// Este bloque declara qué plugins están disponibles para los submódulos (como 'app')
// El 'apply false' significa "no lo apliques aquí, solo hazlo disponible".
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // ¡IMPORTANTE! El plugin del compilador de Compose también se declara aquí.
    alias(libs.plugins.kotlin.compose.compiler) apply false
}