package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AutomationTool
import com.example.data.CommandLog
import com.example.ui.AutomationViewModel
import com.example.ui.AetherisMode
import com.example.ui.BackgroundTask
import com.example.ui.QubitState
import com.example.ui.PenetrationVulnerability
import com.example.ui.SlideItem
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("main_scaffold"),
                    contentWindowInsets = WindowInsets.safeDrawing
                ) { innerPadding ->
                    AetherisDashboard(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Global translations directory for master-level world language compatibility
 */
fun translate(key: String, lang: String): String {
    return when (lang) {
        "Español" -> when (key) {
            "APP_SUBNAME" -> "Agente de automatización de supercomputación"
            "PROMPT_PLACEHOLDER" -> "Pregunta a Aetheris o escribe un comando..."
            "DEV_PREVIEW" -> "VISTA REAL EN VIVO"
            "DEPLOY_BTN" -> "Desplegar en Github"
            "ACTIVE_TASKS" -> "Procesos en Segundo Plano"
            "OFFLINE_MODE" -> "Modo Sin Conexión"
            "PARAMETERS" -> "Parámetros"
            "WAKE_WORD" -> "Palabra de Despertar"
            "HACK_SCAN" -> "Escanear Red"
            "HACK_CONSOLE" -> "CONSOLA DE SEGURIDAD"
            "HACK_PATCH" -> "CORREGIR"
            "HACK_PATCHED" -> "SEGURO"
            "RESEARCH_SEARCH" -> "BUSCAR"
            "RESEARCH_RESULTS" -> "Resultados Encontrados"
            "CREATIVE_GEN" -> "Generar Imagen"
            "CREATIVE_VIDEO" -> "Renderizar Video"
            "SECURE_SHIELD" -> "Protección Local Activa"
            else -> key
        }
        "Français" -> when (key) {
            "APP_SUBNAME" -> "Agent d'automatisation de supercalcul"
            "PROMPT_PLACEHOLDER" -> "Demandez à Aetheris ou tapez une commande..."
            "DEV_PREVIEW" -> "APERÇU EN DIRECT INÉDIT"
            "DEPLOY_BTN" -> "Déployer sur Github"
            "ACTIVE_TASKS" -> "Tâches en Arrière-plan"
            "OFFLINE_MODE" -> "Mode Hors-ligne"
            "PARAMETERS" -> "Paramètres"
            "WAKE_WORD" -> "Mot de Réveil"
            "HACK_SCAN" -> "Scanner Réseau"
            "HACK_CONSOLE" -> "CONSOLE DE SÉCURITÉ"
            "HACK_PATCH" -> "CORRIGER"
            "HACK_PATCHED" -> "SÉCURISÉ"
            "RESEARCH_SEARCH" -> "RECHERCHER"
            "RESEARCH_RESULTS" -> "Résultats Trouvés"
            "CREATIVE_GEN" -> "Générer Image"
            "CREATIVE_VIDEO" -> "Rendre Vidéo"
            "SECURE_SHIELD" -> "Protection Locale Active"
            else -> key
        }
        "Deutsch" -> when (key) {
            "APP_SUBNAME" -> "Supercomputing Automations-Agent"
            "PROMPT_PLACEHOLDER" -> "Frage Aetheris oder tippe einen Befehl..."
            "DEV_PREVIEW" -> "REALZEIT-LIVEVORSCHAU"
            "DEPLOY_BTN" -> "Auf Github Bereitstellen"
            "ACTIVE_TASKS" -> "Hintergrundprozesse"
            "OFFLINE_MODE" -> "Offline-Modus"
            "PARAMETERS" -> "Parameter"
            "WAKE_WORD" -> "Weckwort"
            "HACK_SCAN" -> "Netzwerk Scannen"
            "HACK_CONSOLE" -> "SICHERHEITSKONSOLE"
            "HACK_PATCH" -> "PATCHEN"
            "HACK_PATCHED" -> "SICHER"
            "RESEARCH_SEARCH" -> "SUCHEN"
            "RESEARCH_RESULTS" -> "Gefundene Ergebnisse"
            "CREATIVE_GEN" -> "Bild Generieren"
            "CREATIVE_VIDEO" -> "Video Rendern"
            "SECURE_SHIELD" -> "Lokaler Schutz Aktiv"
            else -> key
        }
        "Русский" -> when (key) {
            "APP_SUBNAME" -> "Агент автоматизации супервычислений"
            "PROMPT_PLACEHOLDER" -> "Спросите Aetheris или введите команду..."
            "DEV_PREVIEW" -> "ЖИВОЙ ПРОСМОТР В РЕАЛЬНОМ ВРЕМЕНИ"
            "DEPLOY_BTN" -> "Развернуть на Github"
            "ACTIVE_TASKS" -> "Фоновые процессы"
            "OFFLINE_MODE" -> "Автономный Режим"
            "PARAMETERS" -> "Параметры"
            "WAKE_WORD" -> "Слово активации"
            "HACK_SCAN" -> "Сканировать"
            "HACK_CONSOLE" -> "КОНСОЛЬ БЕЗОПАСНОСТИ"
            "HACK_PATCH" -> "ИСПРАВИТЬ"
            "HACK_PATCHED" -> "ЗАЩИЩЕНО"
            "RESEARCH_SEARCH" -> "ПОИСК"
            "RESEARCH_RESULTS" -> "Найденные результаты"
            "CREATIVE_GEN" -> "Создать Картинку"
            "CREATIVE_VIDEO" -> "Рендерить Видео"
            "SECURE_SHIELD" -> "Локальная защита активна"
            else -> key
        }
        "中文" -> when (key) {
            "APP_SUBNAME" -> "超算自动化集成代理"
            "PROMPT_PLACEHOLDER" -> "向 Aetheris 提问或输入编译命令..."
            "DEV_PREVIEW" -> "系统实时运行预览"
            "DEPLOY_BTN" -> "部署至 Github 仓库"
            "ACTIVE_TASKS" -> "后台并行执行线程池"
            "OFFLINE_MODE" -> "完全离线模式"
            "PARAMETERS" -> "参数规模"
            "WAKE_WORD" -> "系统唤醒词"
            "HACK_SCAN" -> "扫描局域网络"
            "HACK_CONSOLE" -> "高阶网络安全诊断控制台"
            "HACK_PATCH" -> "一键加固"
            "HACK_PATCHED" -> "安全已加固"
            "RESEARCH_SEARCH" -> "全库检索"
            "RESEARCH_RESULTS" -> "查阅到的学术成果"
            "CREATIVE_GEN" -> "生成渲染图像"
            "CREATIVE_VIDEO" -> "合成渲染视频"
            "SECURE_SHIELD" -> "本地军工级沙箱隔离"
            else -> key
        }
        "日本語" -> when (key) {
            "APP_SUBNAME" -> "スーパーコンピューティング自動化エージェント"
            "PROMPT_PLACEHOLDER" -> "Aetherisに質問するか、コマンドを入力..."
            "DEV_PREVIEW" -> "リアルタイム・ライブプレビュー"
            "DEPLOY_BTN" -> "Githubにデプロイ"
            "ACTIVE_TASKS" -> "バックグラウンド並行タスク"
            "OFFLINE_MODE" -> "オフラインモード"
            "PARAMETERS" -> "パラメータ数"
            "WAKE_WORD" -> "起動ワード"
            "HACK_SCAN" -> "スキャン実行"
            "HACK_CONSOLE" -> "セキュリティコンソール"
            "HACK_PATCH" -> "修正する"
            "HACK_PATCHED" -> "保護済み"
            "RESEARCH_SEARCH" -> "検索実行"
            "RESEARCH_RESULTS" -> "検出されたリサーチ情報"
            "CREATIVE_GEN" -> "画像生成"
            "CREATIVE_VIDEO" -> "ビデオレンダリング"
            "SECURE_SHIELD" -> "ローカル暗号化保護アクティブ"
            else -> key
        }
        "हिन्दी" -> when (key) {
            "APP_SUBNAME" -> "सुपरकंप्यूटिंग स्वचालन एजेंट"
            "PROMPT_PLACEHOLDER" -> "Aetheris से पूछें या कमांड लिखें..."
            "DEV_PREVIEW" -> "वास्तविक समय लाइव पूर्वावलोकन"
            "DEPLOY_BTN" -> "जीथब (Github) पर तैनात करें"
            "ACTIVE_TASKS" -> "पृष्ठभूमि प्रक्रिया कतार"
            "OFFLINE_MODE" -> "ऑफ़लाइन मोड"
            "PARAMETERS" -> "पैरामीटर"
            "WAKE_WORD" -> "सक्रियता शब्द"
            "HACK_SCAN" -> "आईपी स्कैन"
            "HACK_CONSOLE" -> "सुरक्षा कंसोल"
            "HACK_PATCH" -> "ठीक करें"
            "HACK_PATCHED" -> "सुरक्षित"
            "RESEARCH_SEARCH" -> "खोजें"
            "RESEARCH_RESULTS" -> "प्राप्त अनुसंधान परिणाम"
            "CREATIVE_GEN" -> "छवि निर्माण"
            "CREATIVE_VIDEO" -> "वीडियो वीडियो बनाएं"
            "SECURE_SHIELD" -> "स्थानीय सुरक्षा सक्रिय है"
            else -> key
        }
        "العربية" -> when (key) {
            "APP_SUBNAME" -> "وكيل الأتمتة الفائق الذكي"
            "PROMPT_PLACEHOLDER" -> "اسأل أثيريس أو اكتب أمراً..."
            "DEV_PREVIEW" -> "شاشة المعاينة الفورية المباشرة"
            "DEPLOY_BTN" -> "نشر المشروع على Github"
            "ACTIVE_TASKS" -> "العمليات البرمجية قيد التشغيل"
            "OFFLINE_MODE" -> "وضع عدم الاتصال بالشبكة"
            "PARAMETERS" -> "مؤشرات الذكاء الاصطناعي"
            "WAKE_WORD" -> "كلمة الاستدعاء"
            "HACK_SCAN" -> "فحص المنافذ والشبكة"
            "HACK_CONSOLE" -> "لوحة تحكم الأمن السيبراني"
            "HACK_PATCH" -> "إصلاح وحماية"
            "HACK_PATCHED" -> "آمن ومحمي"
            "RESEARCH_SEARCH" -> "بحث سريع"
            "RESEARCH_RESULTS" -> "بيانات المحتوى العلمي الوفير"
            "CREATIVE_GEN" -> "توليد صورة واقعية"
            "CREATIVE_VIDEO" -> "حياكة حركة فيديو"
            "SECURE_SHIELD" -> "حماية الصندوق الرملي المحلي"
            else -> key
        }
        "Português" -> when (key) {
            "APP_SUBNAME" -> "Agente de Automação de Supercomputação"
            "PROMPT_PLACEHOLDER" -> "Pergunte ao Aetheris ou digite um comando..."
            "DEV_PREVIEW" -> "PRÉ-VISUALIZAÇÃO EM TEMPO REAL"
            "DEPLOY_BTN" -> "Implantar no GitHub"
            "ACTIVE_TASKS" -> "Processos em Segundo Plano"
            "OFFLINE_MODE" -> "Modo Offline"
            "PARAMETERS" -> "Parâmetros"
            "WAKE_WORD" -> "Palavra de Ativação"
            "HACK_SCAN" -> "Escanear Portas"
            "HACK_CONSOLE" -> "CONSOLE DE ACCESO SEGURO"
            "HACK_PATCH" -> "CORRIGIR"
            "HACK_PATCHED" -> "SEGURO"
            "RESEARCH_SEARCH" -> "PESQUISAR"
            "RESEARCH_RESULTS" -> "Resultados de Pesquisa"
            "CREATIVE_GEN" -> "Criar Imagem"
            "CREATIVE_VIDEO" -> "Renderizar Vídeo"
            "SECURE_SHIELD" -> "Proteção Física Local Ligada"
            else -> key
        }
        "Italiano" -> when (key) {
            "APP_SUBNAME" -> "Agente di Automazione di Supercalcolo"
            "PROMPT_PLACEHOLDER" -> "Chiedi ad Aetheris o digita un comando..."
            "DEV_PREVIEW" -> "ANTEPRIMA DISPOSITIVO LIVE"
            "DEPLOY_BTN" -> "Rilascia su GitHub"
            "ACTIVE_TASKS" -> "Servizi in Background"
            "OFFLINE_MODE" -> "Modalità Offline"
            "PARAMETERS" -> "Modello Parametri"
            "WAKE_WORD" -> "Nome di Attivazione"
            "HACK_SCAN" -> "Scansiona Rete"
            "HACK_CONSOLE" -> "SISTEMA DI CONTROLLO HACKER"
            "HACK_PATCH" -> "RISOLVI"
            "HACK_PATCHED" -> "PROTETTO"
            "RESEARCH_SEARCH" -> "RICERCA"
            "RESEARCH_RESULTS" -> "Risultati Scientifici Trovati"
            "CREATIVE_GEN" -> "Genera Immagine"
            "CREATIVE_VIDEO" -> "Crea Video"
            "SECURE_SHIELD" -> "Isolamento Locale Attivo"
            else -> key
        }
        else -> when (key) {
            "APP_SUBNAME" -> "Supercomputing Automation Agent"
            "PROMPT_PLACEHOLDER" -> "Ask Aetheris or type a compiler command..."
            "DEV_PREVIEW" -> "REAL-TIME DEVICE PREVIEW"
            "DEPLOY_BTN" -> "Deploy to GitHub Repository"
            "ACTIVE_TASKS" -> "Active Multitasking Workers"
            "OFFLINE_MODE" -> "Offline Sandboxing"
            "PARAMETERS" -> "Parameters System"
            "WAKE_WORD" -> "Voice Wake Word"
            "HACK_SCAN" -> "Scan Port Map"
            "HACK_CONSOLE" -> "SECURITY DIAGNOSTICS CONSOLE"
            "HACK_PATCH" -> "PATCH VULN"
            "HACK_PATCHED" -> "HARDENED"
            "RESEARCH_SEARCH" -> "GROUNDED SEARCH"
            "RESEARCH_RESULTS" -> "Verified Corpus Matches"
            "CREATIVE_GEN" -> "Diffuse Dynamic Image"
            "CREATIVE_VIDEO" -> "Stitch Scene Frames"
            "SECURE_SHIELD" -> "Local Sandbox Secure Shield"
            else -> key
        }
    }
}

@Composable
fun AetherisDashboard(
    modifier: Modifier = Modifier,
    viewModel: AutomationViewModel = viewModel()
) {
    val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()
    val wakeWord by viewModel.wakeWord.collectAsStateWithLifecycle()
    val isParameter2_5T by viewModel.isParameter2_5T.collectAsStateWithLifecycle()
    val isCompiling by viewModel.isCompiling.collectAsStateWithLifecycle()
    val currentPrompt by viewModel.currentPrompt.collectAsStateWithLifecycle()
    val selectedLog by viewModel.selectedLog.collectAsStateWithLifecycle()
    val isVoiceListening by viewModel.isVoiceListening.collectAsStateWithLifecycle()
    val voiceWaves by viewModel.voiceWaves.collectAsStateWithLifecycle()
    val tools by viewModel.tools.collectAsStateWithLifecycle()
    val logs by viewModel.logs.collectAsStateWithLifecycle()
    
    val activeMode by viewModel.activeMode.collectAsStateWithLifecycle()
    val backgroundTasks by viewModel.backgroundTasks.collectAsStateWithLifecycle()
    val qubitStates by viewModel.qubitStates.collectAsStateWithLifecycle()
    
    val hackingConsoleLogs by viewModel.hackingConsoleLogs.collectAsStateWithLifecycle()
    val vulnerabilities by viewModel.vulnerabilities.collectAsStateWithLifecycle()
    val isScanningPorts by viewModel.isScanningPorts.collectAsStateWithLifecycle()
    val portProgress by viewModel.portProgress.collectAsStateWithLifecycle()

    val generatedImages by viewModel.generatedImages.collectAsStateWithLifecycle()
    val videoKeyframes by viewModel.videoKeyframes.collectAsStateWithLifecycle()
    val presentationSlides by viewModel.presentationSlides.collectAsStateWithLifecycle()
    val isRenderingVideo by viewModel.isRenderingVideo.collectAsStateWithLifecycle()
    val videoProgress by viewModel.videoProgress.collectAsStateWithLifecycle()

    val selectedLang by viewModel.selectedLanguage.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current
    var showGithubDialog by remember { mutableStateOf(false) }
    var activeTabPhone by remember { mutableStateOf(0) } // 0: Workspace, 1: Live Preview Device (for phone viewports)

    val customColors = remember {
        object {
            val background = Color(0xFF131314)
            val sidebarBackground = Color(0xFF1E1F20)
            val accentBlue = Color(0xFF00FFCC)
            val borderGray = Color(0xFF30363D)
            val darkCard = Color(0xFF1C1D1E)
            val textLight = Color(0xFFE3E3E3)
            val textMuted = Color(0xFF9E9E9E)
        }
    }

    val config = LocalConfiguration.current
    val isDesktopMode = config.screenWidthDp >= 840

    if (showGithubDialog) {
        AlertDialog(
            onDismissRequest = { showGithubDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, tint = customColors.accentBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("GitHub Deployment Hub", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Your entire Aetheris Supercomputing Agent repo is compiled and fully ready to sync with your personal GitHub account.",
                        fontSize = 12.sp,
                        color = customColors.textLight
                    )
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF0D0E10), RoundedCornerShape(8.dp))
                            .border(1.dp, customColors.borderGray, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("STEPS TO DEPLOY FROM AI STUDIO:", color = customColors.accentBlue, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("1. Look at the horizontal AI Studio Top Navigation Bar.", fontSize = 11.sp, color = Color.White)
                            Text("2. Click the 'Settings / Actions' gear icon on the top-right.", fontSize = 11.sp, color = Color.White)
                            Text("3. Under Integration options, choose 'Push to GitHub'.", fontSize = 11.sp, color = Color.White)
                            Text("4. Authorize your account; your repository will instantly update with zero local files needed!", fontSize = 11.sp, color = Color.White)
                        }
                    }
                    Text("This process links all of your secure parameters, local Room script tables, and multi-language presets to a public/private branch.", fontSize = 10.sp, color = customColors.textMuted)
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showGithubDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = customColors.accentBlue)
                ) {
                    Text("UNDERSTOOD", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = customColors.darkCard,
            textContentColor = Color.White
        )
    }

    Row(modifier = Modifier.fillMaxSize().background(customColors.background)) {
        // Left Column: Permanent Google Gemini-style Navigation Column (Desktop and wide viewports)
        if (isDesktopMode) {
            Column(
                modifier = Modifier
                    .width(260.dp)
                    .fillMaxHeight()
                    .background(customColors.sidebarBackground)
                    .padding(16.dp)
            ) {
                // Application Branding header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                brush = Brush.linearGradient(listOf(Color(0xFF00FFCC), Color(0xFF0D47A1))),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = "Aetheris OS",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "AETHERIS",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp,
                            color = Color.White,
                            lineHeight = 18.sp
                        )
                        Text(
                            text = translate("APP_SUBNAME", selectedLang),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 9.sp,
                            color = customColors.textMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Modes Selection header
                Text(
                    text = "NEURAL MODES",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    color = customColors.textMuted,
                    modifier = Modifier.padding(start = 6.dp, bottom = 8.dp)
                )

                // Render Modes item rows with Gemini touch
                val modes = listOf(
                    Triple(AetherisMode.AGENTS, Icons.Default.SmartToy, "Build with Agents"),
                    Triple(AetherisMode.DEVELOPER, Icons.Default.Security, "Developer & Hacker"),
                    Triple(AetherisMode.RESEARCH, Icons.Default.Science, "Google Scholar"),
                    Triple(AetherisMode.APP_BUILD, Icons.Default.Build, "Compose App Builder"),
                    Triple(AetherisMode.QUANTUM, Icons.Default.RotateLeft, "Quantum Compute"),
                    Triple(AetherisMode.CREATIVE, Icons.Default.Movie, "Creative Studio")
                )

                modes.forEach { (mode, icon, title) ->
                    val isSelected = activeMode == mode
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isSelected) Color(0xFF2D2F31) else Color.Transparent
                            )
                            .clickable { viewModel.setMode(mode) }
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = if (isSelected) customColors.accentBlue else Color.LightGray,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = title,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = if (isSelected) Color.White else Color.LightGray
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Language selection bar inside sidebar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF282A2C))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Language, contentDescription = null, tint = customColors.accentBlue, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    var isLangExpanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Locale: $selectedLang",
                            fontSize = 11.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isLangExpanded = true }
                        )
                        DropdownMenu(
                            expanded = isLangExpanded,
                            onDismissRequest = { isLangExpanded = false },
                            modifier = Modifier.background(customColors.darkCard)
                        ) {
                            viewModel.availableLanguages.forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text(lang, fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Monospace) },
                                    onClick = {
                                        viewModel.setLanguage(lang)
                                        isLangExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Global Configs Switches
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
                ) {
                    Text(translate("OFFLINE_MODE", selectedLang), fontSize = 11.sp, color = customColors.textLight, fontFamily = FontFamily.Monospace)
                    Switch(
                        checked = isOffline,
                        onCheckedChange = { viewModel.setOfflineMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = customColors.accentBlue,
                            uncheckedTrackColor = Color.DarkGray
                        ),
                        modifier = Modifier.scale(0.7f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text("Core 2.5T", fontSize = 11.sp, color = customColors.textLight, fontFamily = FontFamily.Monospace)
                    Switch(
                        checked = isParameter2_5T,
                        onCheckedChange = { viewModel.setParameterMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = customColors.accentBlue,
                            uncheckedTrackColor = Color.DarkGray
                        ),
                        modifier = Modifier.scale(0.7f)
                    )
                }

                // Push to Github CTA (Polished button layout)
                Button(
                    onClick = { showGithubDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2638)),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, customColors.accentBlue)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = "Github Hub",
                        tint = customColors.accentBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = translate("DEPLOY_BTN", selectedLang),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Divider line
            Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(customColors.borderGray))
        }

        // Main UI Frame container: Middle + Right (Live Preview) Screen layout
        Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
            
            // Header bar for Phones viewport / small devices
            if (!isDesktopMode) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(customColors.sidebarBackground)
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(
                                        brush = Brush.linearGradient(listOf(Color(0xFF00FFCC), Color(0xFF0D47A1))),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Terminal, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "AETHERIS",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color.White
                            )
                        }

                        // Github button and Language toggler
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            IconButton(onClick = { showGithubDialog = true }) {
                                Icon(Icons.Default.CloudUpload, contentDescription = "Deploy", tint = customColors.accentBlue, modifier = Modifier.size(20.dp))
                            }
                            // Quick Language menu
                            var isShortLangExpanded by remember { mutableStateOf(false) }
                            Box {
                                IconButton(onClick = { isShortLangExpanded = true }) {
                                    Icon(Icons.Default.Language, contentDescription = "Lang", tint = Color.LightGray, modifier = Modifier.size(20.dp))
                                }
                                DropdownMenu(
                                    expanded = isShortLangExpanded,
                                    onDismissRequest = { isShortLangExpanded = false },
                                    modifier = Modifier.background(customColors.darkCard)
                                ) {
                                    viewModel.availableLanguages.forEach { lang ->
                                        DropdownMenuItem(
                                            text = { Text(lang, fontSize = 11.sp, color = Color.White, fontFamily = FontFamily.Monospace) },
                                            onClick = {
                                                viewModel.setLanguage(lang)
                                                isShortLangExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Mobile Modes Slider
                    ScrollableTabRow(
                        selectedTabIndex = when (activeMode) {
                            AetherisMode.AGENTS -> 0
                            AetherisMode.DEVELOPER -> 1
                            AetherisMode.RESEARCH -> 2
                            AetherisMode.APP_BUILD -> 3
                            AetherisMode.QUANTUM -> 4
                            AetherisMode.CREATIVE -> 5
                        },
                        containerColor = Color.Transparent,
                        edgePadding = 0.dp,
                        contentColor = customColors.accentBlue,
                        indicator = {}
                    ) {
                        Tab(
                            selected = activeMode == AetherisMode.AGENTS,
                            onClick = { viewModel.setMode(AetherisMode.AGENTS) }
                        ) {
                            Text("Agents Hub", modifier = Modifier.padding(10.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                        Tab(
                            selected = activeMode == AetherisMode.DEVELOPER,
                            onClick = { viewModel.setMode(AetherisMode.DEVELOPER) }
                        ) {
                            Text("Dev & Hack", modifier = Modifier.padding(10.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                        Tab(
                            selected = activeMode == AetherisMode.RESEARCH,
                            onClick = { viewModel.setMode(AetherisMode.RESEARCH) }
                        ) {
                            Text("Research", modifier = Modifier.padding(10.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                        Tab(
                            selected = activeMode == AetherisMode.APP_BUILD,
                            onClick = { viewModel.setMode(AetherisMode.APP_BUILD) }
                        ) {
                            Text("App Builder", modifier = Modifier.padding(10.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                        Tab(
                            selected = activeMode == AetherisMode.QUANTUM,
                            onClick = { viewModel.setMode(AetherisMode.QUANTUM) }
                        ) {
                            Text("Quantum", modifier = Modifier.padding(10.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                        Tab(
                            selected = activeMode == AetherisMode.CREATIVE,
                            onClick = { viewModel.setMode(AetherisMode.CREATIVE) }
                        ) {
                            Text("Creative", modifier = Modifier.padding(10.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Phone screen toggler: 0: Workspace Settings Controls 1: Device Live Preview Frame
                    TabRow(
                        selectedTabIndex = activeTabPhone,
                        containerColor = Color(0xFF131415),
                        contentColor = customColors.accentBlue
                    ) {
                        Tab(selected = activeTabPhone == 0, onClick = { activeTabPhone = 0 }) {
                            Text("ACTIVE WORKSPACE", modifier = Modifier.padding(8.dp), fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        }
                        Tab(selected = activeTabPhone == 1, onClick = { activeTabPhone = 1 }) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                                Box(modifier = Modifier.size(6.dp).background(customColors.accentBlue, CircleShape))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("LIVE PREVIEW", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }
                }
            }

            // Split Panels: Left Area is Workspace Panel, Right Area is live Device Preview Device Frame
            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                
                // Left Panel: Dynamic workspace based on chosen mode
                if (isDesktopMode || activeTabPhone == 0) {
                    Column(
                        modifier = Modifier
                            .weight(1.1f)
                            .fillMaxHeight()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Title header for the workspace
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val activeTitle = when (activeMode) {
                                AetherisMode.AGENTS -> "BUILD WITH AGENTS - MULTI-NODE DIRECTORY"
                                AetherisMode.DEVELOPER -> "DEVELOPER & PENETRATION SECURITY"
                                AetherisMode.RESEARCH -> "GOOGLE GROUNDED RESEARCH"
                                AetherisMode.APP_BUILD -> "NATIVE COMPOSE APK WRITER"
                                AetherisMode.QUANTUM -> "QUANTUM TELEMETRY GATE MATRIX"
                                AetherisMode.CREATIVE -> "CREATIVE DESKTOP MEDIA WORKSPACE"
                            }
                            Icon(
                                imageVector = when (activeMode) {
                                    AetherisMode.AGENTS -> Icons.Default.SmartToy
                                    AetherisMode.DEVELOPER -> Icons.Default.Security
                                    AetherisMode.RESEARCH -> Icons.Default.Science
                                    AetherisMode.APP_BUILD -> Icons.Default.Build
                                    AetherisMode.QUANTUM -> Icons.Default.RotateLeft
                                    AetherisMode.CREATIVE -> Icons.Default.Movie
                                },
                                contentDescription = null,
                                tint = customColors.accentBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = activeTitle,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        // Sandbox container scroll
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(customColors.darkCard, RoundedCornerShape(12.dp))
                                .border(1.dp, customColors.borderGray, RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            when (activeMode) {
                                AetherisMode.AGENTS -> {
                                    BuildWithAgentsDashboard(
                                        viewModel = viewModel,
                                        onNavigateToMode = { viewModel.setMode(it) },
                                        customColors = customColors,
                                        selectedLang = selectedLang
                                    )
                                }
                                AetherisMode.DEVELOPER -> {
                                    DevModeWorkspace(
                                        tools = tools,
                                        logs = logs,
                                        selectedLog = selectedLog,
                                        onLogSelect = { viewModel.selectLog(it) },
                                        onClearLogs = { viewModel.clearHistory() },
                                        onToolExecute = { tool ->
                                            viewModel.executeCommand("Compile and test automation tool: " + tool.name)
                                        },
                                        hackingConsoleLogs = hackingConsoleLogs,
                                        vulnerabilities = vulnerabilities,
                                        isScanningPorts = isScanningPorts,
                                        portProgress = portProgress,
                                        onRunScan = { viewModel.runInteractiveScan() },
                                        onPatchVulnerability = { viewModel.patchTargetVulnerability(it) },
                                        onClearHackerLogs = { viewModel.clearHackerLogs() },
                                        selectedLang = selectedLang,
                                        customColors = customColors,
                                        onRegisterTool = { name, desc, code, cat ->
                                            viewModel.registerCustomAutomationTool(name, desc, code, cat)
                                        },
                                        onDeleteTool = { id ->
                                            viewModel.deleteAutomationTool(id)
                                        }
                                    )
                                }
                                AetherisMode.RESEARCH -> {
                                    ResearchModeWorkspace(
                                        logs = logs,
                                        onTriggerSearch = { query ->
                                            viewModel.executeCommand("Search academic literature database for: " + query)
                                        },
                                        selectedLang = selectedLang,
                                        customColors = customColors
                                    )
                                }
                                AetherisMode.APP_BUILD -> {
                                    AppBuilderModeWorkspace(
                                        onTriggerBuild = { code ->
                                            viewModel.executeCommand("Compile layout container scripts: " + code)
                                        },
                                        selectedLang = selectedLang,
                                        customColors = customColors
                                    )
                                }
                                AetherisMode.QUANTUM -> {
                                    QuantumWorkspace(
                                        qubitStates = qubitStates,
                                        onApplyGate = { index, gate -> viewModel.applyQuantumGate(index, gate) },
                                        customColors = customColors
                                    )
                                }
                                AetherisMode.CREATIVE -> {
                                    CreativeStudioPanel(
                                        generatedImages = generatedImages,
                                        videoKeyframes = videoKeyframes,
                                        presentationSlides = presentationSlides,
                                        isRenderingVideo = isRenderingVideo,
                                        videoProgress = videoProgress,
                                        onImagePromptSubmit = { viewModel.triggerImageGeneration(it) },
                                        onTriggerVideo = { viewModel.triggerVideoRender() },
                                        onAddSlide = { title, content, theme -> viewModel.addNewSlide(title, content, theme) },
                                        onDeleteSlide = { viewModel.deleteSlide(it) },
                                        selectedLang = selectedLang,
                                        customColors = customColors
                                    )
                                }
                            }
                        }

                        // Gemini Voice Radar sound frequency waves
                        VoiceInterceptionWavePanel(
                            isVoiceListening = isVoiceListening,
                            voiceWaves = voiceWaves,
                            isCompiling = isCompiling,
                            onVoiceTap = { viewModel.startVoiceListening() },
                            customColors = customColors,
                            selectedLang = selectedLang
                        )

                        // Command terminal input sandbox panel
                        CommandInputTextRow(
                            currentPrompt = currentPrompt,
                            onPromptChange = { viewModel.updatePrompt(it) },
                            onSubmitAndCompile = {
                                keyboardController?.hide()
                                viewModel.executeCommand()
                            },
                            isCompiling = isCompiling,
                            customColors = customColors,
                            selectedLang = selectedLang
                        )
                    }
                }

                // Right Panel: Live simulator preview screen mimicking on-device execution in real time
                if (isDesktopMode || activeTabPhone == 1) {
                    Column(
                        modifier = Modifier
                            .weight(0.9f)
                            .fillMaxHeight()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
                        ) {
                            Text(
                                translate("DEV_PREVIEW", selectedLang),
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = customColors.accentBlue
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color(0xFF39FF14), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "LIVE ONLINE",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.LightGray
                                )
                            }
                        }

                        // Outer interactive telephone chassis device frame wrapping live updates and state triggers
                        SmartphoneDeviceBezel(
                            modifier = Modifier.weight(1f),
                            activeMode = activeMode,
                            vulnerabilities = vulnerabilities,
                            presentationSlides = presentationSlides,
                            generatedImages = generatedImages,
                            isRenderingVideo = isRenderingVideo,
                            videoProgress = videoProgress,
                            videoKeyframes = videoKeyframes,
                            qubitStates = qubitStates,
                            backgroundTasks = backgroundTasks,
                            selectedLang = selectedLang,
                            isCompiling = isCompiling,
                            customColors = customColors
                        )

                        // Multitasking active processes monitoring block
                        ActiveProcessesFooter(
                            tasks = backgroundTasks,
                            onClearAllTasks = { viewModel.clearTasks() },
                            customColors = customColors,
                            selectedLang = selectedLang
                        )
                    }
                }
            }
        }
    }
}

/**
 * Custom-made telephone device container to provide live feedback of real-time developments
 */
@Composable
fun SmartphoneDeviceBezel(
    modifier: Modifier = Modifier,
    activeMode: AetherisMode,
    vulnerabilities: List<PenetrationVulnerability>,
    presentationSlides: List<SlideItem>,
    generatedImages: List<String>,
    isRenderingVideo: Boolean,
    videoProgress: Float,
    videoKeyframes: List<String>,
    qubitStates: List<QubitState>,
    backgroundTasks: List<BackgroundTask>,
    selectedLang: String,
    isCompiling: Boolean,
    customColors: Any
) {
    val accent = Color(0xFF00FFCC)
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0D0E10), RoundedCornerShape(28.dp))
            .border(5.dp, Color(0xFF2B2C2E), RoundedCornerShape(28.dp))
            .padding(10.dp)
    ) {
        // Inner screen canvas of device
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF08090A))
        ) {
            // Simulated Status Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF131416))
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "09:41",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                // Device notch pill in center
                Box(
                    modifier = Modifier
                        .size(width = 54.dp, height = 12.dp)
                        .background(Color.Black, RoundedCornerShape(20.dp))
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Wifi, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(10.dp))
                    Icon(Icons.Default.BatteryChargingFull, contentDescription = null, tint = accent, modifier = Modifier.size(11.dp))
                }
            }

            // Real Time Simulated Output Page depending on active workspace
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                if (isCompiling) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = accent, strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("COMPILING CODE...", color = accent, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        Text("Constructing high-precision sandbox registers", color = Color.Gray, fontSize = 8.sp, textAlign = TextAlign.Center)
                    }
                } else {
                    when (activeMode) {
                        AetherisMode.AGENTS -> {
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("ACTIVE AGENTS OVERVIEW", color = accent, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("INFRASTRUCTURE NODE: Google AI Studio", color = Color.Gray, fontSize = 7.sp, fontFamily = FontFamily.Monospace)
                                
                                val stats = listOf(
                                    "Antigravity Preview" to "READY",
                                    "AI Talk Radio" to "ONLINE",
                                    "Customer Support" to "LISTENING",
                                    "Data Analyst" to "MAPPED",
                                    "Repo Maintainer" to "IDLE",
                                    "Document Processor" to "SYNCED"
                                )
                                stats.forEach { (agentName, status) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFF121315), RoundedCornerShape(4.dp))
                                            .padding(4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(agentName, color = Color.White, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (status == "IDLE") Color(0x1a8a8a8a) else Color(0x3300FFCC),
                                                    RoundedCornerShape(3.dp)
                                                )
                                                .border(
                                                    width = 0.5.dp,
                                                    color = if (status == "IDLE") Color.Gray else accent,
                                                    shape = RoundedCornerShape(3.dp)
                                                )
                                                .padding(horizontal = 4.dp, vertical = 1.dp)
                                        ) {
                                            Text(
                                                status,
                                                color = if (status == "IDLE") Color.Gray else accent,
                                                fontSize = 6.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        AetherisMode.DEVELOPER -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text("PENETRATION RUNTIME MAP", color = accent, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(6.dp))
                                
                                vulnerabilities.forEach { vuln ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 3.dp)
                                            .background(Color(0xFF121315), RoundedCornerShape(6.dp))
                                            .padding(6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(vuln.service, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                            Text(vuln.ipAddress, color = Color.Gray, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (vuln.status == "SECURED") Color(0x3339FF14) else Color(0x33FF5252),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                vuln.status,
                                                color = if (vuln.status == "SECURED") Color(0xFF39FF14) else Color(0xFFFF5252),
                                                fontSize = 7.sp,
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF191A1D), RoundedCornerShape(6.dp))
                                        .padding(6.dp)
                                ) {
                                    Text(
                                        "Audit secure system firewall logs instantly.",
                                        fontSize = 8.sp,
                                        color = Color.LightGray,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                        AetherisMode.RESEARCH -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text("GROUNDED ACADEMIC CORPUS", color = accent, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(6.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .background(Color(0xFF121315), RoundedCornerShape(8.dp))
                                        .padding(8.dp)
                                ) {
                                    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        item {
                                            Text("DOCUMENT VECTOR MATCH:", color = Color.LightGray, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                            Text("Title: 2-Trillion Dense Agent on consumer tech", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                        }
                                        item {
                                            Divider(color = Color.DarkGray, thickness = 0.5.dp)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                "Through layered custom segment loading, the system enables standard local SSDs to operate as high-precision virtual RAM partitions, retaining full-weight accuracy without excessive internet payload transport.",
                                                color = Color.Gray,
                                                fontSize = 9.sp,
                                                lineHeight = 11.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        AetherisMode.APP_BUILD -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("COMPOSE LIVE APK PREVIEW", color = accent, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(14.dp))
                                
                                // Interactive Mock Component inside the simulated emulator live window
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .background(
                                            brush = Brush.verticalGradient(listOf(Color(0xFF1F2228), Color(0xFF101216))),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .border(1.dp, accent, RoundedCornerShape(12.dp))
                                        .padding(12.dp)
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text("Aetheris Widget", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                            Box(modifier = Modifier.size(6.dp).background(accent, CircleShape))
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text("Status: Core Active\nNode: Client Sandbox IP\nEncryption: 256-bit AES", color = Color.LightGray, fontSize = 8.sp, fontFamily = FontFamily.Monospace, modifier = Modifier.fillMaxWidth())
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Button(
                                            onClick = {},
                                            modifier = Modifier.fillMaxWidth().height(26.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = accent),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Text("EXECUTE LIVE COMPILER", fontSize = 8.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                        }
                                    }
                                }
                            }
                        }
                        AetherisMode.QUANTUM -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text("BLOCH SPHERE VECTOR GRAPH", color = accent, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .background(Color(0xFF121315), RoundedCornerShape(8.dp))
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        qubitStates.forEach { q ->
                                            val probColor = if (q.probability1 > 0.5f) Color(0xFF00FFCC) else Color.White
                                            Row(
                                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(q.label, color = probColor, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                                    Text("Prob: ${(q.probability1 * 100).toInt()}%", color = accent, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                                                    Text("Phase: ${q.phaseDegrees}°", color = Color.LightGray, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                                                }
                                            }
                                            LinearProgressIndicator(
                                                progress = { q.probability1 },
                                                modifier = Modifier.fillMaxWidth().height(3.dp),
                                                color = accent,
                                                trackColor = Color.DarkGray
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        AetherisMode.CREATIVE -> {
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("CREATIVE DISPLAY RENDER", color = accent, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                
                                // Carousel Presentation view inside the device phone wrapper
                                if (presentationSlides.isNotEmpty()) {
                                    val currentSlide = presentationSlides.first()
                                    Card(
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E283A)),
                                        border = BorderStroke(1.dp, accent),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                                Text("SLIDE #${currentSlide.slideNumber}", color = accent, fontSize = 8.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                                Text(currentSlide.backgroundTheme, color = Color.Gray, fontSize = 7.sp, fontFamily = FontFamily.Monospace)
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(currentSlide.title, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(currentSlide.content, color = Color.LightGray, fontSize = 8.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)
                                        }
                                    }
                                }

                                // Custom Mock Cinema Video Frame player inside telephone preview
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .background(Color.Black, RoundedCornerShape(8.dp))
                                        .border(0.5.dp, Color.DarkGray, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isRenderingVideo) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            CircularProgressIndicator(color = accent, modifier = Modifier.size(14.dp), strokeWidth = 1.5.dp)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text("RENDERING MP4: ${(videoProgress * 100).toInt()}%", color = Color.White, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                                        }
                                    } else {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = accent, modifier = Modifier.size(24.dp))
                                            Text(videoKeyframes.firstOrNull() ?: "Timeline Safe", color = Color.LightGray, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                                        }
                                    }
                                }

                                // Photo Canvas inside device phone emulator wrapper
                                if (generatedImages.isNotEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(55.dp)
                                            .background(Color(0xFF121315), RoundedCornerShape(8.dp))
                                            .padding(6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Icon(Icons.Default.Image, contentDescription = null, tint = accent, modifier = Modifier.size(16.dp))
                                            Text(generatedImages.first(), color = Color.White, fontSize = 8.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, fontFamily = FontFamily.Monospace)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Developer Workspace Component
 */
@Composable
fun DevModeWorkspace(
    tools: List<AutomationTool>,
    logs: List<CommandLog>,
    selectedLog: CommandLog?,
    onLogSelect: (CommandLog?) -> Unit,
    onClearLogs: () -> Unit,
    onToolExecute: (AutomationTool) -> Unit,
    hackingConsoleLogs: List<String>,
    vulnerabilities: List<PenetrationVulnerability>,
    isScanningPorts: Boolean,
    portProgress: Float,
    onRunScan: () -> Unit,
    onPatchVulnerability: (String) -> Unit,
    onClearHackerLogs: () -> Unit,
    selectedLang: String,
    customColors: Any,
    onRegisterTool: (String, String, String, String) -> Unit,
    onDeleteTool: (Int) -> Unit
) {
    var sectionTab by remember { mutableStateOf(0) } // 0: Penetration, 1: Script Tools, 2: Run Terminal Logs
    val accent = Color(0xFF00FFCC)

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Flat M3 Tabs
        TabRow(
            selectedTabIndex = sectionTab,
            containerColor = Color.Transparent,
            contentColor = accent
        ) {
            Tab(selected = sectionTab == 0, onClick = { sectionTab = 0 }) {
                Text("Ethical Pentest", modifier = Modifier.padding(8.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
            Tab(selected = sectionTab == 1, onClick = { sectionTab = 1 }) {
                Text("Automation Scripts", modifier = Modifier.padding(8.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
            Tab(selected = sectionTab == 2, onClick = { sectionTab = 2 }) {
                Text("Compiler Logs", modifier = Modifier.padding(8.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when (sectionTab) {
                0 -> {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                translate("HACK_CONSOLE", selectedLang),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                fontFamily = FontFamily.Monospace
                            )
                            Button(
                                onClick = onRunScan,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2638)),
                                border = BorderStroke(1.dp, accent),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(28.dp),
                                enabled = !isScanningPorts,
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text(translate("HACK_SCAN", selectedLang), fontSize = 9.sp, color = accent, fontFamily = FontFamily.Monospace)
                            }
                        }

                        if (isScanningPorts) {
                            LinearProgressIndicator(
                                progress = { portProgress },
                                modifier = Modifier.fillMaxWidth().height(3.dp),
                                color = accent,
                                trackColor = Color.DarkGray
                            )
                        }

                        // Vulnerability list scroll
                        LazyColumn(modifier = Modifier.height(120.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            items(vulnerabilities) { vuln ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF1E1F20), RoundedCornerShape(6.dp))
                                        .border(1.dp, Color(0xFF2D2F31), RoundedCornerShape(6.dp))
                                        .padding(6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("${vuln.ipAddress}:${vuln.port}", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                        Text("Service: ${vuln.service}", color = Color.Gray, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                                    }
                                    Button(
                                        onClick = { onPatchVulnerability(vuln.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF282A2D)),
                                        enabled = vuln.status == "VULNERABLE",
                                        modifier = Modifier.height(24.dp),
                                        contentPadding = PaddingValues(horizontal = 6.dp)
                                    ) {
                                        Text(
                                            text = if (vuln.status == "SECURED") translate("HACK_PATCHED", selectedLang) else translate("HACK_PATCH", selectedLang),
                                            color = if (vuln.status == "SECURED") Color(0xFF39FF14) else accent,
                                            fontSize = 8.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }

                        // Terminal console display box
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("CONSOLE RUNTIME DEEP LOGS", fontSize = 8.sp, color = Color.Gray, fontFamily = FontFamily.Monospace)
                            Text("FLUSH", fontSize = 8.sp, color = Color.Red, fontFamily = FontFamily.Monospace, modifier = Modifier.clickable { onClearHackerLogs() })
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(Color.Black, RoundedCornerShape(8.dp))
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            LazyColumn(reverseLayout = true) {
                                items(hackingConsoleLogs.asReversed()) { log ->
                                    Text(log, color = Color.LightGray, fontSize = 9.sp, fontFamily = FontFamily.Monospace, lineHeight = 11.sp)
                                }
                            }
                        }
                    }
                }
                1 -> {
                    var showCreateForm by remember { mutableStateOf(false) }
                    var newScriptName by remember { mutableStateOf("") }
                    var newScriptDesc by remember { mutableStateOf("") }
                    var newScriptCode by remember { mutableStateOf("") }
                    var newScriptCategory by remember { mutableStateOf("Custom Tasker") }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("AUTOMATION CONTRACTS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray, fontFamily = FontFamily.Monospace)
                            Button(
                                onClick = { showCreateForm = !showCreateForm },
                                colors = ButtonDefaults.buttonColors(containerColor = if (showCreateForm) Color(0xFFC62828) else Color(0xFF1E2638)),
                                border = BorderStroke(1.dp, accent),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(26.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Icon(
                                    imageVector = if (showCreateForm) Icons.Default.Close else Icons.Default.Add,
                                    contentDescription = null,
                                    tint = accent,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (showCreateForm) "Cancel" else "Add Script",
                                    fontSize = 9.sp,
                                    color = accent,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }

                        if (showCreateForm) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF121315), RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text("SECURE AES-256 LOCAL SCRIPT ENCRYPTER", fontSize = 9.sp, color = Color.LightGray, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                
                                OutlinedTextField(
                                    value = newScriptName,
                                    onValueChange = { newScriptName = it },
                                    textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                                    placeholder = { Text("Script Name (e.g., Ledger Vault Automation)", color = Color.Gray, fontSize = 10.sp) },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accent, unfocusedBorderColor = Color.DarkGray)
                                )

                                OutlinedTextField(
                                    value = newScriptDesc,
                                    onValueChange = { newScriptDesc = it },
                                    textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                                    placeholder = { Text("Brief Script Description", color = Color.Gray, fontSize = 10.sp) },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accent, unfocusedBorderColor = Color.DarkGray)
                                )

                                OutlinedTextField(
                                    value = newScriptCode,
                                    onValueChange = { newScriptCode = it },
                                    textStyle = LocalTextStyle.current.copy(color = Color(0xFF39FF14), fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                                    placeholder = { Text("# Paste custom secure python automation scripts inline...", color = Color.Gray, fontSize = 9.sp) },
                                    modifier = Modifier.fillMaxWidth().weight(1f).heightIn(min = 80.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accent, unfocusedBorderColor = Color.DarkGray)
                                )

                                Button(
                                    onClick = {
                                        if (newScriptName.isNotBlank() && newScriptCode.isNotBlank()) {
                                            onRegisterTool(newScriptName, newScriptDesc, newScriptCode, newScriptCategory)
                                            newScriptName = ""
                                            newScriptDesc = ""
                                            newScriptCode = ""
                                            showCreateForm = false
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = accent),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.fillMaxWidth().height(32.dp)
                                ) {
                                    Text("SAVE ENCRYPTED PROTOCOL", fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                }
                            }
                        }

                        // Scrollable list of prebuilt and custom offline tools
                        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(tools) { tool ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF1E1F20), RoundedCornerShape(8.dp))
                                        .border(1.dp, Color(0xFF2D2F31), RoundedCornerShape(8.dp))
                                        .clickable { onToolExecute(tool) }
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(tool.name, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .background(
                                                        if (tool.isSystemTool) Color(0xFF1E2638) else Color(0x3300FFCC),
                                                        RoundedCornerShape(4.dp)
                                                    )
                                                    .border(
                                                        width = 1.dp,
                                                        color = if (tool.isSystemTool) Color.Gray else accent,
                                                        shape = RoundedCornerShape(4.dp)
                                                    )
                                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = if (tool.isSystemTool) "SYSTEM" else "SECURED USER",
                                                    fontSize = 7.sp,
                                                    color = if (tool.isSystemTool) Color.LightGray else accent,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = FontFamily.Monospace
                                                )
                                            }
                                        }
                                        Text(tool.description, color = Color.LightGray, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                    
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        if (!tool.isSystemTool) {
                                            IconButton(
                                                onClick = { onDeleteTool(tool.id) },
                                                modifier = Modifier.size(24.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete automation script",
                                                    tint = Color(0xFFE57373),
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Execute simulation",
                                            tint = accent,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                2 -> {
                    // Compiler terminal history logs
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("AI COMPILER SCRIPTS HISTORY", fontSize = 9.sp, color = Color.Gray, fontFamily = FontFamily.Monospace)
                            Text("MUTE ALL", fontSize = 8.sp, color = Color.Red, fontFamily = FontFamily.Monospace, modifier = Modifier.clickable { onClearLogs() })
                        }

                        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            items(logs) { log ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF121315), RoundedCornerShape(6.dp))
                                        .padding(8.dp)
                                ) {
                                    Text("Command: ${log.prompt}", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                    Text("Terminal stdout output:\n${log.outputLog}", color = Color.Gray, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Google Research Workspace Component
 */
@Composable
fun ResearchModeWorkspace(
    logs: List<CommandLog>,
    onTriggerSearch: (String) -> Unit,
    selectedLang: String,
    customColors: Any
) {
    var searchField by remember { mutableStateOf("") }
    val accent = Color(0xFF00FFCC)

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchField,
                onValueChange = { searchField = it },
                textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace),
                placeholder = { Text("Search 2T architecture studies...", fontSize = 11.sp, color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.weight(1.5f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accent,
                    unfocusedBorderColor = Color.DarkGray
                )
            )
            Button(
                onClick = {
                    if (searchField.isNotBlank()) {
                        onTriggerSearch(searchField)
                        searchField = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = accent),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(translate("RESEARCH_SEARCH", selectedLang), fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }
        }

        Text(translate("RESEARCH_RESULTS", selectedLang).uppercase(), fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF0F0F10), RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                item {
                    Text("🌐 [Grounded Index] AI-Offloaded Multi-Core Systems on Smartphones", color = accent, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    Text("A research study describing standard local disk memory mapping techniques mapping 1GB-10GB dense state layers sequentially to eliminate cloud network latency and protect user telemetry files offline.", color = Color.LightGray, fontSize = 9.sp)
                }
                item {
                    Divider(color = Color.DarkGray)
                }
                item {
                    Text("🌐 [Grounded Index] Quantized EXL2 Formatting & Client Compilers", color = accent, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    Text("Details on running complex 8-bit networks in standard mobile phone containers by allocating dynamic block caches safely on consumer Android hardware.", color = Color.LightGray, fontSize = 9.sp)
                }
            }
        }
    }
}

/**
 * App Builder Workspace Component
 */
@Composable
fun AppBuilderModeWorkspace(
    onTriggerBuild: (String) -> Unit,
    selectedLang: String,
    customColors: Any
) {
    var generatedText by remember { mutableStateOf("import androidx.compose.material3.*\nimport androidx.compose.runtime.*\n\n@Composable\nfun MyBuiltAppComponent() {\n    Button(onClick = {}) {\n        Text(\"Build Complete!\")\n    }\n}") }
    val accent = Color(0xFF00FFCC)

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("NATIVE COMPOSE APK WRITER SOURCE CODE", fontSize = 9.sp, color = Color.Gray, fontFamily = FontFamily.Monospace)
            Button(
                onClick = { onTriggerBuild(generatedText) },
                colors = ButtonDefaults.buttonColors(containerColor = accent),
                modifier = Modifier.height(28.dp),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                Text("ASSEMBLE", fontSize = 9.sp, color = Color.Black, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Black, RoundedCornerShape(8.dp))
                .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = generatedText,
                onValueChange = { generatedText = it },
                textStyle = LocalTextStyle.current.copy(color = Color(0xFF39FF14), fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                modifier = Modifier.fillMaxSize(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }
    }
}

/**
 * Quantum Registers Workspace Component
 */
@Composable
fun QuantumWorkspace(
    qubitStates: List<QubitState>,
    onApplyGate: (Int, String) -> Unit,
    customColors: Any
) {
    val accent = Color(0xFF00FFCC)

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("LOCAL SIMULATOR REGISTERS & HADAMARD MATRIX", fontSize = 10.sp, color = Color.Gray, fontFamily = FontFamily.Monospace)

        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(qubitStates) { q ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E1F20), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val highlightColor = if (q.probability1 > 0.5f) Color(0xFF00FFCC) else Color.White
                    val gatesStr = q.gatesApplied.joinToString(", ").ifEmpty { "None" }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(q.label, color = highlightColor, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        Text("Gates: $gatesStr", color = Color.LightGray, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("H", "X", "Z", "CNOT", "RESET").forEach { gate ->
                            Button(
                                onClick = { onApplyGate(q.index, gate) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF131415)),
                                border = BorderStroke(0.5.dp, accent),
                                modifier = Modifier.weight(1f).height(24.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(gate, fontSize = 8.sp, color = accent, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Creative Studio panel (Images, Videos, Presentations)
 */
@Composable
fun CreativeStudioPanel(
    generatedImages: List<String>,
    videoKeyframes: List<String>,
    presentationSlides: List<SlideItem>,
    isRenderingVideo: Boolean,
    videoProgress: Float,
    onImagePromptSubmit: (String) -> Unit,
    onTriggerVideo: () -> Unit,
    onAddSlide: (String, String, String) -> Unit,
    onDeleteSlide: (Int) -> Unit,
    selectedLang: String,
    customColors: Any
) {
    var innerTabIndex by remember { mutableStateOf(0) } // 0: Slides, 1: Images, 2: Video Stitch
    val accent = Color(0xFF00FFCC)

    var textTitle by remember { mutableStateOf("") }
    var textDesc by remember { mutableStateOf("") }
    val slideTheme = "Cyan Slate"

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TabRow(
            selectedTabIndex = innerTabIndex,
            containerColor = Color.Transparent,
            contentColor = accent
        ) {
            Tab(selected = innerTabIndex == 0, onClick = { innerTabIndex = 0 }) {
                Text("Presentation Slides", modifier = Modifier.padding(6.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
            Tab(selected = innerTabIndex == 1, onClick = { innerTabIndex = 1 }) {
                Text("Images Prompt", modifier = Modifier.padding(6.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
            Tab(selected = innerTabIndex == 2, onClick = { innerTabIndex = 2 }) {
                Text("Video Timeline", modifier = Modifier.padding(6.dp), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when (innerTabIndex) {
                0 -> {
                    // Presentation slide creation panel
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            OutlinedTextField(
                                value = textTitle,
                                onValueChange = { textTitle = it },
                                placeholder = { Text("Slide Title", fontSize = 10.sp, color = Color.Gray) },
                                textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                                singleLine = true,
                                modifier = Modifier.weight(1.5f),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accent, unfocusedBorderColor = Color.DarkGray)
                            )
                            OutlinedTextField(
                                value = textDesc,
                                onValueChange = { textDesc = it },
                                placeholder = { Text("Slide content detail...", fontSize = 10.sp, color = Color.Gray) },
                                textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                                singleLine = true,
                                modifier = Modifier.weight(2f),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accent, unfocusedBorderColor = Color.DarkGray)
                            )
                            Button(
                                onClick = {
                                    if (textTitle.isNotBlank()) {
                                        onAddSlide(textTitle, textDesc, slideTheme)
                                        textTitle = ""
                                        textDesc = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = accent),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text("ADD", fontSize = 9.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                        }

                        // Created Slides listing
                        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            items(presentationSlides) { slide ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF1E1F20), RoundedCornerShape(6.dp))
                                        .padding(6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("#" + slide.slideNumber + " - " + slide.title, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                        Text(slide.content, color = Color.LightGray, fontSize = 8.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                    IconButton(
                                        onClick = { onDeleteSlide(slide.slideNumber) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Image prompt generation panel
                    var imgPrompt by remember { mutableStateOf("") }
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            OutlinedTextField(
                                value = imgPrompt,
                                onValueChange = { imgPrompt = it },
                                placeholder = { Text("E.g. neon matrix circuit landscape...", fontSize = 10.sp, color = Color.Gray) },
                                textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accent, unfocusedBorderColor = Color.DarkGray)
                            )
                            Button(
                                onClick = {
                                    if (imgPrompt.isNotBlank()) {
                                        onImagePromptSubmit(imgPrompt)
                                        imgPrompt = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = accent)
                            ) {
                                Text(translate("CREATIVE_GEN", selectedLang), fontSize = 9.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                        }

                        // Display history
                        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            items(generatedImages) { img ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF1E1F20), RoundedCornerShape(6.dp))
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = accent, modifier = Modifier.size(14.dp))
                                    Text(img, color = Color.LightGray, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                }
                            }
                        }
                    }
                }
                2 -> {
                    // Video timelines render triggers
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onTriggerVideo,
                            colors = ButtonDefaults.buttonColors(containerColor = accent),
                            enabled = !isRenderingVideo
                        ) {
                            Icon(Icons.Default.MovieFilter, contentDescription = null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(translate("CREATIVE_VIDEO", selectedLang), fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        }

                        if (isRenderingVideo) {
                            LinearProgressIndicator(
                                progress = { videoProgress },
                                modifier = Modifier.fillMaxWidth(0.8f).height(4.dp),
                                color = accent,
                                trackColor = Color.DarkGray
                            )
                        }

                        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            items(videoKeyframes) { frame ->
                                Text("🎞️ " + frame, color = Color.Gray, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Microphone voice processing control card
 */
@Composable
fun VoiceInterceptionWavePanel(
    isVoiceListening: Boolean,
    voiceWaves: List<Float>,
    isCompiling: Boolean,
    onVoiceTap: () -> Unit,
    customColors: Any,
    selectedLang: String
) {
    val accent = Color(0xFF00FFCC)

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1D1E)),
        border = BorderStroke(1.dp, Color(0xFF333538)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconButton(
                onClick = onVoiceTap,
                modifier = Modifier
                    .size(36.dp)
                    .background(if (isVoiceListening) accent else Color(0xFF282A2D), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice mic",
                    tint = if (isVoiceListening) Color.Black else Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isVoiceListening) "INTERCEPTING ORAL BIOFREQUENCY..." else translate("WAKE_WORD", selectedLang) + " INTERPRETER",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = if (isVoiceListening) accent else Color.White
                )
                
                // Sound frequencies waves graph
                Row(
                    modifier = Modifier.height(18.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    voiceWaves.forEach { height ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(height)
                                .background(if (isVoiceListening) accent else Color.DarkGray, RoundedCornerShape(1.dp))
                        )
                    }
                }
            }
        }
    }
}

/**
 * Console custom coding prompt bar
 */
@Composable
fun CommandInputTextRow(
    currentPrompt: String,
    onPromptChange: (String) -> Unit,
    onSubmitAndCompile: () -> Unit,
    isCompiling: Boolean,
    customColors: Any,
    selectedLang: String
) {
    val accent = Color(0xFF00FFCC)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = currentPrompt,
            onValueChange = onPromptChange,
            textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace),
            placeholder = { Text(translate("PROMPT_PLACEHOLDER", selectedLang), fontSize = 11.sp, color = Color.Gray) },
            singleLine = true,
            modifier = Modifier.weight(1f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accent,
                unfocusedBorderColor = Color(0xFF333538),
                focusedContainerColor = Color(0xFF131415),
                unfocusedContainerColor = Color(0xFF131415)
            ),
            shape = RoundedCornerShape(8.dp)
        )

        IconButton(
            onClick = onSubmitAndCompile,
            modifier = Modifier
                .size(40.dp)
                .background(accent, RoundedCornerShape(8.dp)),
            enabled = !isCompiling
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Search",
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

/**
 * Parallel active processes details row
 */
@Composable
fun ActiveProcessesFooter(
    tasks: List<BackgroundTask>,
    onClearAllTasks: () -> Unit,
    customColors: Any,
    selectedLang: String
) {
    val accent = Color(0xFF00FFCC)

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1F20)),
        border = BorderStroke(0.5.dp, Color.DarkGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    translate("ACTIVE_TASKS", selectedLang),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    "TERMINATE",
                    fontSize = 8.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.clickable { onClearAllTasks() }
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            if (tasks.isEmpty()) {
                Text("All high-precision registers are idle.", color = Color.LightGray, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
            } else {
                LazyColumn(modifier = Modifier.height(55.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(tasks.take(5)) { task ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(task.name, fontSize = 8.sp, color = Color.White, fontFamily = FontFamily.Monospace, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                                LinearProgressIndicator(
                                    progress = { task.progress },
                                    modifier = Modifier.width(40.dp).height(2.dp),
                                    color = accent,
                                    trackColor = Color.DarkGray
                                )
                                Text(((task.progress * 100).toInt()).toString() + "%", fontSize = 7.sp, color = accent, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class AgentItem(
    val id: String,
    val name: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color,
    val initialLetter: String,
    val modeTarget: AetherisMode?
)

@Composable
fun BuildWithAgentsDashboard(
    viewModel: AutomationViewModel,
    onNavigateToMode: (AetherisMode) -> Unit,
    customColors: Any,
    selectedLang: String
) {
    var selectedTab by remember { mutableStateOf(1) } // 0: Models, 1: Agents
    var selectedAgentId by remember { mutableStateOf("antigravity") }
    val accent = Color(0xFF00FFCC)

    val agentsList = remember {
        listOf(
            AgentItem("antigravity", "Antigravity Preview", "Preview your app interactively and iterate on design with compiler actions", Icons.Default.PhoneAndroid, Color(0xFFFFC107), "A", AetherisMode.APP_BUILD),
            AgentItem("talk_radio", "AI Talk Radio", "Engage in voice-driven live synthesis discussions, interactive radio broadcasts & dynamic audio logs", Icons.Default.Radio, Color(0xFF2196F3), "R", null),
            AgentItem("support", "Customer Support", "Configure multi-turn customer agent helpdesks, interactive chats, and support protocols", Icons.Default.Headset, Color(0xFFE91E63), "S", null),
            AgentItem("analyst", "Data Analyst", "Analyze database queries, virtual execution charts, and inspect telemetry patterns", Icons.Default.BarChart, Color(0xFF4CAF50), "D", null),
            AgentItem("processor", "Document Processor", "Parse and summarize presentations, slide decks, and literary works in high performance", Icons.Default.Description, Color(0xFFF44336), "P", AetherisMode.RESEARCH),
            AgentItem("maintainer", "Repo Maintainer", "Automate codebase refactoring, security shielding, and local dependency configurations", Icons.Default.Build, Color(0xFFFF9800), "M", AetherisMode.DEVELOPER)
        )
    }

    val currentSelectedAgent = agentsList.firstOrNull { it.id == selectedAgentId } ?: agentsList.first()
    val chunks = remember { agentsList.chunked(2) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // AI Studio Header Title & Mode Switches
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Build with Agents",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = "Initialize custom multi-turn workflows & debug sandboxed compiler actions",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily.SansSerif
                    )
                }

                // Models | Agents dynamic pill switcher
                Row(
                    modifier = Modifier
                        .background(Color(0xFF1E2022), RoundedCornerShape(20.dp))
                        .padding(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedTab == 0) Color(0xFF2D2F31) else Color.Transparent)
                            .clickable { selectedTab = 0 }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Models",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTab == 0) accent else Color.Gray,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedTab == 1) Color(0xFF2D2F31) else Color.Transparent)
                            .clickable { selectedTab = 1 }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Agents",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTab == 1) accent else Color.Gray,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        if (selectedTab == 0) {
            // MODELS TAB
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF161719)),
                    border = BorderStroke(1.dp, Color(0xFF2D3033))
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("AVAILABLE COMPUTE MODELS", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        
                        val modelSpecs = listOf(
                            Triple("Aetheris-Pro 2.5T MoE", "Default multi-tiered weights designed for local compilation, security audits, and state management operations. Powered by offline sandbox adapters.", "2.5 Trillion Parameters"),
                            Triple("Gemma-2B Local SQLite Spec", "Hyper-optimized offline token predictor built into custom Room DAO structures, allowing encrypted SQL analysis inside the local ledger.", "2.0 Billion Parameters"),
                            Triple("Gemini-2.5-Flash (API Token Tunnel)", "Connected via secure cloud gateway for live multi-turn research, deck architecture, and system scripting.", "Cloud Gateway Integrated")
                        )

                        modelSpecs.forEach { (name, desc, capacity) ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF0F1012), RoundedCornerShape(8.dp))
                                    .border(1.dp, Color(0xFF202225), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = accent, fontFamily = FontFamily.Monospace)
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0x3300FFCC), RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(capacity, fontSize = 7.sp, color = accent, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(desc, fontSize = 9.sp, color = Color.LightGray)
                            }
                        }
                    }
                }
            }
        } else {
            // AGENTS TAB
            items(chunks) { rowAgents ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowAgents.forEach { agent ->
                        val isSelected = agent.id == selectedAgentId
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(125.dp)
                                .clickable { selectedAgentId = agent.id },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFF1D2230) else Color(0xFF161719)
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isSelected) accent else Color(0xFF2D3033)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(agent.color.copy(alpha = 0.2f), CircleShape)
                                            .border(1.dp, agent.color, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = agent.icon,
                                            contentDescription = null,
                                            tint = agent.color,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFF252729), RoundedCornerShape(4.dp))
                                            .padding(horizontal = 5.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = agent.initialLetter,
                                            color = Color.LightGray,
                                            fontSize = 7.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }

                                Column {
                                    Text(
                                        text = agent.name,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontFamily = FontFamily.SansSerif,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = agent.description,
                                        fontSize = 8.5.sp,
                                        color = Color.LightGray,
                                        lineHeight = 10.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Expanded Interactive Agent Sandbox Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF101214)),
                    border = BorderStroke(1.dp, accent.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0xFF121417), Color(0xFF090A0C))
                                )
                            )
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(currentSelectedAgent.color, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${currentSelectedAgent.name.uppercase()} SANDBOX RUNTIME",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            
                            if (currentSelectedAgent.modeTarget != null) {
                                Button(
                                    onClick = { onNavigateToMode(currentSelectedAgent.modeTarget) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2638)),
                                    border = BorderStroke(1.dp, accent),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.height(26.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp)
                                ) {
                                    Text("Open Workspace", fontSize = 8.5.sp, color = accent, fontFamily = FontFamily.Monospace)
                                }
                            }
                        }

                        Divider(color = Color.DarkGray, thickness = 0.5.dp)

                        when (currentSelectedAgent.id) {
                            "antigravity" -> {
                                AntigravitySandboxView(viewModel, onNavigateToMode)
                            }
                            "talk_radio" -> {
                                TalkRadioSandboxView(viewModel)
                            }
                            "support" -> {
                                CustomerSupportSandboxView()
                            }
                            "analyst" -> {
                                DataAnalystSandboxView(viewModel)
                            }
                            "processor" -> {
                                DocumentProcessorSandboxView()
                            }
                            "maintainer" -> {
                                RepoMaintainerSandboxView(viewModel, onNavigateToMode)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AntigravitySandboxView(
    viewModel: AutomationViewModel,
    onNavigateToMode: (AetherisMode) -> Unit
) {
    var hzSlider by remember { mutableStateOf(60f) }
    var containerLayout by remember { mutableStateOf("Responsive Row") }
    val accent = Color(0xFF00FFCC)

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Frame Rate Refresh Speed: ${hzSlider.toInt()} Hz", fontSize = 9.sp, color = Color.LightGray)
            Text("Renderer: Hardware Accel GL", fontSize = 9.sp, color = Color.Gray, fontFamily = FontFamily.Monospace)
        }

        Slider(
            value = hzSlider,
            onValueChange = { hzSlider = it },
            valueRange = 10f..120f,
            colors = SliderDefaults.colors(
                thumbColor = accent,
                activeTrackColor = accent,
                inactiveTrackColor = Color.DarkGray
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Responsive Row", "Split Panel", "Canonical Column").forEach { layout ->
                val selected = containerLayout == layout
                Button(
                    onClick = { containerLayout = layout },
                    modifier = Modifier.weight(1f).height(24.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selected) accent else Color(0xFF1E2024),
                        contentColor = if (selected) Color.Black else Color.LightGray
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(layout, fontSize = 8.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
                }
            }
        }

        Button(
            onClick = { onNavigateToMode(AetherisMode.APP_BUILD) },
            modifier = Modifier.fillMaxWidth().height(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accent),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text("LAUNCH INTERACTIVE EMULATOR PREVIEW", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
fun TalkRadioSandboxView(viewModel: AutomationViewModel) {
    var radioPreset by remember { mutableStateOf("102.3 FM - Aetheris Core") }
    var activeTranscript by remember { mutableStateOf("Audio broadcast receiver idle. Click below to synthesize.") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("102.3 FM - Core", "98.5 FM - Scholar", "106.1 FM - Hacking").forEach { station ->
                val selected = radioPreset == station
                Button(
                    onClick = { radioPreset = station },
                    modifier = Modifier.weight(1f).height(24.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selected) Color(0xFF2196F3) else Color(0xFF1E2024)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(station, fontSize = 8.sp, color = Color.White)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D0E10), RoundedCornerShape(8.dp))
                .border(0.5.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("TRANSCRIPT TUNER STREAM", fontSize = 7.5.sp, color = Color.Gray, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                Text(activeTranscript, fontSize = 9.5.sp, color = Color.LightGray, lineHeight = 11.sp)
            }
        }

        Button(
            onClick = {
                viewModel.startVoiceListening()
                activeTranscript = "Broadcast Station $radioPreset:\n\"Aetheris voice synthesis core active. Standard 256-bit AES cryptographic protocols have secured the local SQLite datastore structures. The current workspace telemetry looks optimized and secure.\""
            },
            modifier = Modifier.fillMaxWidth().height(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text("SYNTHESIZE LIVE RADIO BROADCAST (AUDIO RUNTIME)", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
fun CustomerSupportSandboxView() {
    var messages by remember { mutableStateOf(listOf(
        "Support Bot" to "Hello! I am your AI Customer Support specialist. Select a telemetry issue from below or type a custom question."
    )) }
    var userPrompt by remember { mutableStateOf("") }
    val accent = Color(0xFF00FFCC)

    val presets = listOf(
        "Vulnerability flagged",
        "Deploy scripts",
        "Generate summary deck"
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp)
                .background(Color(0xFF090A0C), RoundedCornerShape(8.dp))
                .border(0.5.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(messages) { (sender, text) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = sender.uppercase(),
                            fontSize = 7.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (sender == "Support Bot") Color(0xFFE91E63) else accent,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = text,
                            fontSize = 9.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            presets.forEach { query ->
                Button(
                    onClick = {
                        val reply = when (query) {
                            "Vulnerability flagged" -> "Secure protocol loaded. All database entities are encrypted with AES-256. Navigate to maintaining dashboard to secure database ports."
                            "Deploy scripts" -> "Repository setup verified. Deploy using Settings -> 'Deploy to Github Branch' anytime from the top bar."
                            "Generate summary deck" -> "Presentation engine active. Added summary sliders containing core supercomputing ledger states."
                            else -> "Query resolved."
                        }
                        messages = messages + ("User" to query) + ("Support Bot" to reply)
                    },
                    modifier = Modifier.weight(1f).height(24.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2024)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(query, fontSize = 7.5.sp, color = Color.LightGray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userPrompt,
                onValueChange = { userPrompt = it },
                textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 9.5.sp),
                placeholder = { Text("Ask offline assistant query...", color = Color.Gray, fontSize = 9.5.sp) },
                singleLine = true,
                modifier = Modifier.weight(1f).height(40.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accent,
                    unfocusedBorderColor = Color.DarkGray
                )
            )

            Button(
                onClick = {
                    if (userPrompt.isNotBlank()) {
                        val prompt = userPrompt
                        userPrompt = ""
                        messages = messages + ("User" to prompt) + ("Support Bot" to "Understood. Mapping query to local offline compilers. Executing sandbox analysis...")
                    }
                },
                modifier = Modifier.height(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accent),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ) {
                Text("Send", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }
        }
    }
}

@Composable
fun DataAnalystSandboxView(viewModel: AutomationViewModel) {
    val tools by viewModel.tools.collectAsStateWithLifecycle()
    val logs by viewModel.logs.collectAsStateWithLifecycle()
    var analyticsReport by remember { mutableStateOf("Analyst reporting module initialized. Run automated analysis below.") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D0E10)),
                border = BorderStroke(0.5.dp, Color.DarkGray)
            ) {
                Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("LEDGERS", fontSize = 7.5.sp, color = Color.Gray, fontFamily = FontFamily.Monospace)
                    Text("${logs.size}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D0E10)),
                border = BorderStroke(0.5.dp, Color.DarkGray)
            ) {
                Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SCRIPTS", fontSize = 7.5.sp, color = Color.Gray, fontFamily = FontFamily.Monospace)
                    Text("${tools.size}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D0E10)),
                border = BorderStroke(0.5.dp, Color.DarkGray)
            ) {
                Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("INTEGRITY", fontSize = 7.5.sp, color = Color.Gray, fontFamily = FontFamily.Monospace)
                    Text("100%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF090A0C), RoundedCornerShape(8.dp))
                .border(0.5.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Text(analyticsReport, fontSize = 9.sp, color = Color.LightGray, lineHeight = 11.sp)
        }

        Button(
            onClick = {
                analyticsReport = "ANALYTICS REPORT SUMMARY:\n- Encrypted Python scripts count in Room: ${tools.size}\n- Execution command entries compiled: ${logs.size}\n- Datastore status: Fully protected & unexposed.\n- Optimization target: Ideal system compile speed."
            },
            modifier = Modifier.fillMaxWidth().height(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text("TRIGGER AUTOMATED SQLITE ANALYTICS AUDIT", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
fun DocumentProcessorSandboxView() {
    var selectedDoc by remember { mutableStateOf("Persist.pdf") }
    var displaySummary by remember { mutableStateOf("Select an academic PDF file above to extract intelligent document chunks.") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Persist.pdf", "Entangle.pdf").forEach { doc ->
                val selected = selectedDoc == doc
                Button(
                    onClick = {
                        selectedDoc = doc
                        displaySummary = if (doc == "Persist.pdf") {
                            "SUMMARY REPORT (Supercomputing_Secured_Persist.pdf):\n- Explains AES-256 local database wrappers inside Room persistence to secure python script automation tools.\n- Highlights key-derivation algorithms for uncompromised offline computing registers."
                        } else {
                            "SUMMARY REPORT (Unilateral_Qubit_Entanglement.pdf):\n- Outlines high fidelity Hadamard-gate matrices on 12-qubit systems with CNOT parity controls.\n- Analyzes Bloch Sphere coordinate trajectories in high-precision simulation registers."
                        }
                    },
                    modifier = Modifier.weight(1f).height(24.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selected) Color(0xFFF44336) else Color(0xFF1E2024)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(doc, fontSize = 8.5.sp, color = Color.White)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D0E10), RoundedCornerShape(8.dp))
                .border(0.5.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            Text(displaySummary, fontSize = 9.sp, color = Color.LightGray, lineHeight = 11.sp)
        }
    }
}

@Composable
fun RepoMaintainerSandboxView(
    viewModel: AutomationViewModel,
    onNavigateToMode: (AetherisMode) -> Unit
) {
    var maintainerStatus by remember { mutableStateOf("Refactoring module idle. Click to audit.") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            val criteria = listOf(
                "AES-256 Ledger Keys Loaded" to true,
                "Room DAO Entity Scheme Verification" to true,
                "Multitasking Background Threads Pool" to true
            )
            criteria.forEach { (text, verified) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text, fontSize = 8.sp, color = Color.LightGray)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF090A0C), RoundedCornerShape(8.dp))
                .border(0.5.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
                .testTag("maintainer_status_box")
        ) {
            Text(maintainerStatus, fontSize = 9.sp, color = Color.LightGray, lineHeight = 11.sp)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Button(
                onClick = {
                    maintainerStatus = "CODE ARCHITECTURE SYSTEM REPORT:\n- No unencrypted python files allowed outside secure Space.\n- All database transactions are structured safely.\n- Clean Architecture & MVVM layers fully comply with Material 3 standards."
                },
                modifier = Modifier.weight(1f).height(32.dp).testTag("maintainer_audit_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("RUN AUDIT", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }
            
            Button(
                onClick = { onNavigateToMode(AetherisMode.DEVELOPER) },
                modifier = Modifier.weight(1f).height(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2638)),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, Color(0xFFFF9800))
            ) {
                Text("DEV WORKSPACE", color = Color(0xFFFF9800), fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }
        }
    }
}
