package com.gw.bluetooth.main.ui.connect.model

sealed interface ConnectIntent {
    object Scan : ConnectIntent
}