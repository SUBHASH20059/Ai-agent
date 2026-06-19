package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.AutomationRepository
import com.example.data.AutomationTool
import com.example.data.CommandLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AetherisMode {
    AGENTS,    // Build with Agents Dashboard Hub
    DEVELOPER, // General Terminal Coding & Senior Ethical Hacker Unit
    RESEARCH,  // Google Scholar Corpus Research & Multi-Language Database
    APP_BUILD, // Mobile Android Kotlin Compiler & UI Sandbox
    QUANTUM,   // High Precision Hadamard & CNOT Qubit Registers
    CREATIVE   // Image, Video Generation & Presentation Deck Architect
}

data class BackgroundTask(
    val id: String,
    val name: String,
    val mode: AetherisMode,
    val progress: Float, // 0.0 to 1.0
    val status: String,  // "RUNNING", "COMPLETED", "FAILED"
    val timestamp: Long = System.currentTimeMillis()
)

data class QubitState(
    val index: Int,
    val label: String,
    val probability1: Float,
    val phaseDegrees: Float,
    val gatesApplied: List<String>
)

data class PenetrationVulnerability(
    val id: String,
    val ipAddress: String,
    val port: Int,
    val service: String,
    val threatLevel: String, // "CRITICAL", "HIGH", "SECURED"
    val status: String // "VULNERABLE", "PATCHING", "SECURED"
)

data class SlideItem(
    val slideNumber: Int,
    val title: String,
    val content: String,
    val backgroundTheme: String
)

class AutomationViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = AutomationRepository(db.automationDao())

    val tools: StateFlow<List<AutomationTool>> = repository.allTools
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val logs: StateFlow<List<CommandLog>> = repository.allLogs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // --- State Toggles ---
    private val _isOffline = MutableStateFlow(false)
    val isOffline: StateFlow<Boolean> = _isOffline.asStateFlow()

    private val _wakeWord = MutableStateFlow("Aetheris")
    val wakeWord: StateFlow<String> = _wakeWord.asStateFlow()

    private val _isParameter2_5T = MutableStateFlow(true)
    val isParameter2_5T: StateFlow<Boolean> = _isParameter2_5T.asStateFlow()

    // --- Operational States ---
    private val _isCompiling = MutableStateFlow(false)
    val isCompiling: StateFlow<Boolean> = _isCompiling.asStateFlow()

    private val _currentPrompt = MutableStateFlow("")
    val currentPrompt: StateFlow<String> = _currentPrompt.asStateFlow()

    private val _selectedLog = MutableStateFlow<CommandLog?>(null)
    val selectedLog: StateFlow<CommandLog?> = _selectedLog.asStateFlow()

    // --- Voice Recognition Simulator State ---
    private val _isVoiceListening = MutableStateFlow(false)
    val isVoiceListening: StateFlow<Boolean> = _isVoiceListening.asStateFlow()

    private val _voiceWaves = MutableStateFlow<List<Float>>(List(15) { 0.1f })
    val voiceWaves: StateFlow<List<Float>> = _voiceWaves.asStateFlow()

    // --- Multitasking Active Nodes & Modes ---
    private val _activeMode = MutableStateFlow(AetherisMode.AGENTS)
    val activeMode: StateFlow<AetherisMode> = _activeMode.asStateFlow()

    private val _backgroundTasks = MutableStateFlow<List<BackgroundTask>>(emptyList())
    val backgroundTasks: StateFlow<List<BackgroundTask>> = _backgroundTasks.asStateFlow()

    // --- Global World Languages Configuration ---
    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    val availableLanguages = listOf(
        "English", "Español", "Français", "Deutsch", "Русский", "中文", "日本語", "हिन्दी", "العربية", "Português", "Italiano"
    )

    // --- Ethical Hacking state targets ---
    private val _hackingConsoleLogs = MutableStateFlow<List<String>>(
        listOf(
            "[INIT] Kali-Aetheris Suite initialized successfully.",
            "[INFO] Firewalls and deep backplanes mapped.",
            "[IDLE] Awaiting network audit instruction..."
        )
    )
    val hackingConsoleLogs: StateFlow<List<String>> = _hackingConsoleLogs.asStateFlow()

    private val _vulnerabilities = MutableStateFlow<List<PenetrationVulnerability>>(
        listOf(
            PenetrationVulnerability("V-101", "192.168.1.45", 80, "HTTP-Apache", "HIGH", "VULNERABLE"),
            PenetrationVulnerability("V-102", "192.168.1.99", 22, "SSH OpenSSH", "CRITICAL", "VULNERABLE"),
            PenetrationVulnerability("V-103", "10.0.0.12", 443, "SSL Cert Expiry", "MEDIUM", "SECURED")
        )
    )
    val vulnerabilities: StateFlow<List<PenetrationVulnerability>> = _vulnerabilities.asStateFlow()

    private val _isScanningPorts = MutableStateFlow(false)
    val isScanningPorts: StateFlow<Boolean> = _isScanningPorts.asStateFlow()

    private val _portProgress = MutableStateFlow(0f)
    val portProgress: StateFlow<Float> = _portProgress.asStateFlow()

    // --- Creative Studio states: Presentations, Image and Video Creation ---
    private val _generatedImages = MutableStateFlow<List<String>>(
        listOf(
            "Retro Synthwave Horizon Over Digital Grid",
            "Golden Cyberpunk Android Holding Quantum Processor",
            "Mysterious Neural Pattern Connecting Space Galaxies"
        )
    )
    val generatedImages: StateFlow<List<String>> = _generatedImages.asStateFlow()

    private val _videoKeyframes = MutableStateFlow<List<String>>(
        listOf(
            "Keyframe 00:00 (Logo Intro Animation - Nebula)",
            "Keyframe 00:04 (Dynamic Graph Connections Zooming)",
            "Keyframe 00:09 (Hacker shell command log layout render)",
            "Keyframe 00:15 (Outro Credit slide fading - Aetheris Core)"
        )
    )
    val videoKeyframes: StateFlow<List<String>> = _videoKeyframes.asStateFlow()

    private val _presentationSlides = MutableStateFlow<List<SlideItem>>(
        listOf(
            SlideItem(1, "Aetheris AI Unified Universe", "Master professional workspace covering Neural Networks, High-dimensional parameters, Quantum gates, and Cybersecurity operations.", "Cyan-Slate Gradient"),
            SlideItem(2, "Superposition Quantum Synergy", "Utilizing local simulator registers with Hadamard and CNOT entanglement to speed operations with infinite precision.", "Quantum Neon Blue"),
            SlideItem(3, "Secure Private Infrastructure", "With local compiler isolation, Aetheris achieves 0% data exposure. Highly certified zero-cloud vulnerability pipeline.", "Carbon Guard")
        )
    )
    val presentationSlides: StateFlow<List<SlideItem>> = _presentationSlides.asStateFlow()

    private val _isRenderingVideo = MutableStateFlow(false)
    val isRenderingVideo: StateFlow<Boolean> = _isRenderingVideo.asStateFlow()

    private val _videoProgress = MutableStateFlow(0f)
    val videoProgress: StateFlow<Float> = _videoProgress.asStateFlow()


    // --- Quantum Computing Simulator Registers ---
    private val _qubitStates = MutableStateFlow(
        listOf(
            QubitState(0, "q₀ (Control Node)", 0.0f, 0f, emptyList()),
            QubitState(1, "q₁ (Memory Register)", 0.0f, 0f, emptyList()),
            QubitState(2, "q₂ (State Synapse)", 0.0f, 0f, emptyList())
        )
    )
    val qubitStates: StateFlow<List<QubitState>> = _qubitStates.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedInitialToolsIfEmpty()
        }

        // Multitasking simulation ticker
        viewModelScope.launch {
            while (true) {
                delay(300)
                _backgroundTasks.value = _backgroundTasks.value.map { task ->
                    if (task.status == "RUNNING") {
                        val nextProgress = (task.progress + Random.nextFloat() * 0.15f).coerceAtMost(1.0f)
                        val nextStatus = if (nextProgress >= 1.0f) "COMPLETED" else "RUNNING"
                        task.copy(progress = nextProgress, status = nextStatus)
                    } else {
                        task
                    }
                }
            }
        }
    }

    fun setMode(mode: AetherisMode) {
        _activeMode.value = mode
    }

    fun setLanguage(lang: String) {
        _selectedLanguage.value = lang
        appendHackingLog("[LANG] System translation context shifted to: $lang")
    }

    fun setOfflineMode(value: Boolean) {
        _isOffline.value = value
    }

    fun setWakeWord(word: String) {
        if (word.isNotBlank()) {
            _wakeWord.value = word.trim()
        }
    }

    fun setParameterMode(value: Boolean) {
        _isParameter2_5T.value = value
    }

    fun updatePrompt(text: String) {
        _currentPrompt.value = text
    }

    fun selectLog(log: CommandLog?) {
        _selectedLog.value = log
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearLogs()
            _selectedLog.value = null
        }
    }

    /**
     * Executes compile operation. Creates structured python and runs simulation diagnostics.
     * Also spawns active multitasking background loops to prove multitasking core capability.
     */
    fun executeCommand(customPrompt: String? = null) {
        val targetPrompt = customPrompt ?: _currentPrompt.value
        if (targetPrompt.isBlank()) return

        viewModelScope.launch {
            _isCompiling.value = true
            _currentPrompt.value = "" // clear input bar
            
            // Spawn appropriate multitasking tasks on background queue
            spawnModeTasks(targetPrompt)

            // Artificial compilation trace
            delay(1000) 
            
            val log = repository.compileCommand(
                prompt = targetPrompt,
                isOffline = _isOffline.value,
                wakeWord = _wakeWord.value
            )
            _selectedLog.value = log
            _isCompiling.value = false
        }
    }

    private fun spawnModeTasks(prompt: String) {
        val mode = _activeMode.value
        val list = _backgroundTasks.value.toMutableList()
        val suffix = if (prompt.length > 25) prompt.take(22) + "..." else prompt

        when (mode) {
            AetherisMode.AGENTS -> {
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Coordinate Agent Framework: $suffix", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Orchestrate Multi-Node State", mode, 0.0f, "RUNNING"))
            }
            AetherisMode.RESEARCH -> {
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Crawl Web Nodes: $suffix", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Semantic Grounding Syncer", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Google Knowledge Matcher", mode, 0.0f, "RUNNING"))
            }
            AetherisMode.APP_BUILD -> {
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Compose Compiler Daemon", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Resolve build.gradle.kts", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Gradle Resource Packer", mode, 0.0f, "RUNNING"))
            }
            AetherisMode.DEVELOPER -> {
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Verify PyCompiler Sandbox", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Check ADB Multi-Device Port", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Penetration Buffer Audit", mode, 0.0f, "RUNNING"))
            }
            AetherisMode.QUANTUM -> {
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Evaluate Amplitude Vector", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Qiskit Pipeline Transpiler", mode, 0.0f, "RUNNING"))
            }
            AetherisMode.CREATIVE -> {
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Ffmpeg Render Multiplexer", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "DALL-E Prompt Encoder", mode, 0.0f, "RUNNING"))
                list.add(0, BackgroundTask(Random.nextInt(100000).toString(), "Widescreen Presentation Slicer", mode, 0.0f, "RUNNING"))
            }
        }

        // Keep maximum 15 tasks in history to prevent state overflow
        _backgroundTasks.value = list.take(15)
    }

    fun spawnManualTask(name: String) {
        val list = _backgroundTasks.value.toMutableList()
        list.add(0, BackgroundTask(Random.nextInt(100000).toString(), name, _activeMode.value, 0.0f, "RUNNING"))
        _backgroundTasks.value = list.take(15)
    }

    fun clearTasks() {
        _backgroundTasks.value = emptyList()
    }

    // --- Senior Ethical Hacker Console & Logic Toolset ---
    fun appendHackingLog(message: String) {
        _hackingConsoleLogs.value = (_hackingConsoleLogs.value + message).takeLast(40)
    }

    fun clearHackerLogs() {
        _hackingConsoleLogs.value = listOf("[ALERT] Console buffer flushed by operator.")
    }

    fun runInteractiveScan() {
        if (_isScanningPorts.value) return
        _isScanningPorts.value = true
        _portProgress.value = 0f
        appendHackingLog("[SCAN] Commencing raw Wi-Fi & Backplane map analysis...")

        viewModelScope.launch {
            val targets = listOf("80 (HTTP)", "22 (SSH)", "443 (SSL)", "3306 (MySQL)", "8080 (Proxy)")
            for (step in 1..10) {
                delay(300)
                _portProgress.value = step / 10f
                if (step % 2 == 0) {
                    appendHackingLog("[SCAN] Scanning socket subnet targets: port ${targets.random()}")
                }
            }
            
            // Randomly reset a vulnerability status to vulnerable to make it interactive and fun
            _vulnerabilities.value = _vulnerabilities.value.map { vuln ->
                if (vuln.id == "V-102") vuln.copy(status = "VULNERABLE", threatLevel = "CRITICAL") else vuln
            }
            
            appendHackingLog("[WARN] Deep scan finished. Identified Critical Vulnerability at Port 22 SSH (OpenSSH).")
            _isScanningPorts.value = false
        }
    }

    fun patchTargetVulnerability(targetId: String) {
        _vulnerabilities.value = _vulnerabilities.value.map { vuln ->
            if (vuln.id == targetId) {
                appendHackingLog("[PATCH] Securing Vulnerability ${vuln.id}. Overwriting secure session buffers...")
                vuln.copy(status = "PATCHING", threatLevel = "MEDIUM")
            } else vuln
        }

        viewModelScope.launch {
            delay(1500)
            _vulnerabilities.value = _vulnerabilities.value.map { vuln ->
                if (vuln.id == targetId) {
                    appendHackingLog("[SECURE] Patch successful! ${vuln.id} ${vuln.service} is now 100% hardened.")
                    vuln.copy(status = "SECURED", threatLevel = "SECURED")
                } else vuln
            }
        }
    }

    // --- AI Creative Studio: Image, Video & Presentations ---
    fun triggerImageGeneration(prompt: String) {
        if (prompt.isBlank()) return
        appendHackingLog("[CREATIVE] Initiating diffusion canvas model for: '$prompt'")
        
        viewModelScope.launch {
            spawnManualTask("Render Image Canvas: '$prompt'")
            delay(1400)
            val formatText = prompt.trim()
            _generatedImages.value = listOf(formatText) + _generatedImages.value.take(4)
            appendHackingLog("[COMPLETED] High-precision image successfully assembled.")
        }
    }

    fun triggerVideoRender() {
        if (_isRenderingVideo.value) return
        _isRenderingVideo.value = true
        _videoProgress.value = 0f
        appendHackingLog("[VIDEO] Spawning parallel multi-frame interpolation timeline render...")

        viewModelScope.launch {
            for (step in 1..10) {
                delay(300)
                _videoProgress.value = step / 10f
                if (step == 3) {
                    appendHackingLog("[VIDEO] Frame Interpolator: Stitching Keyframe 1 & Keyframe 2")
                } else if (step == 7) {
                    appendHackingLog("[VIDEO] Quantizing dynamic telemetry audio visual wave frames")
                }
            }
            _isRenderingVideo.value = false
            appendHackingLog("[SUCCESS] Rendered 15-second senior production cinematic clip to sandbox container.")
        }
    }

    fun addNewSlide(title: String, content: String, theme: String) {
        val nextNum = _presentationSlides.value.size + 1
        val item = SlideItem(nextNum, title, content, theme)
        _presentationSlides.value = _presentationSlides.value + item
        appendHackingLog("[PRESENTATION] Appended slide: #$nextNum - $title")
    }

    fun deleteSlide(slideNum: Int) {
        _presentationSlides.value = _presentationSlides.value
            .filter { it.slideNumber != slideNum }
            .mapIndexed { idx, slide -> slide.copy(slideNumber = idx + 1) }
        appendHackingLog("[PRESENTATION] Removed slide. Slides index recalculated.")
    }


    /**
     * Quantum Gate Application Logic (Quantum Simulator Core)
     */
    fun applyQuantumGate(qubitIndex: Int, gateName: String) {
        _qubitStates.value = _qubitStates.value.map { qubit ->
            if (qubit.index == qubitIndex) {
                val nextGates = qubit.gatesApplied + gateName
                var nextProb = qubit.probability1
                var nextPhase = qubit.phaseDegrees

                when (gateName) {
                    "H" -> {
                        nextProb = 0.5f
                        nextPhase = 0f
                    }
                    "X" -> {
                        nextProb = 1.0f - qubit.probability1
                        nextPhase = if (nextProb > 0.5f) 180f else 0f
                    }
                    "Z" -> {
                        nextPhase = (qubit.phaseDegrees + 180f).rem(360f)
                    }
                    "CNOT" -> {
                        nextProb = 0.8f
                        nextPhase = 90f
                    }
                    "RESET" -> {
                        nextProb = 0.0f
                        nextPhase = 0f
                        return@map qubit.copy(probability1 = 0f, phaseDegrees = 0f, gatesApplied = emptyList())
                    }
                }
                qubit.copy(probability1 = nextProb, phaseDegrees = nextPhase, gatesApplied = nextGates)
            } else {
                qubit
            }
        }
    }

    /**
     * Triggers dynamic simulated voice recognition buffer checks.
     */
    fun startVoiceListening() {
        if (_isVoiceListening.value) return
        _isVoiceListening.value = true

        viewModelScope.launch {
            var durationTicks = 0
            while (durationTicks < 40) {
                _voiceWaves.value = List(15) { Random.nextFloat() * 0.9f + 0.1f }
                delay(100)
                durationTicks++
            }
            
            val presetSpokenCommands = listOf(
                "Mute volume instantly and schedule backplane optimization",
                "Encrypt my secure personal ledger database immediately",
                "Deploy clipboard trend scraper and format as telemetry JSON",
                "Run deep task diagnostics in device workspace",
                "Check multi-app security integrity offline"
            )
            val recognizedSpeech = presetSpokenCommands.random()
            
            _voiceWaves.value = List(15) { 0.1f }
            _isVoiceListening.value = false
            
            executeCommand(recognizedSpeech)
        }
    }

    fun registerCustomAutomationTool(name: String, desc: String, code: String, category: String) {
        viewModelScope.launch {
            val customTool = AutomationTool(
                name = name,
                description = desc,
                pythonScript = code,
                category = category,
                sampleTrigger = "Trigger $name offline task",
                isSystemTool = false
            )
            repository.insertTool(customTool)
        }
    }

    fun deleteAutomationTool(id: Int) {
        viewModelScope.launch {
            repository.deleteToolById(id)
        }
    }
}
