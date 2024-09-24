package com.example.sprint4;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprint4.databinding.ActivitySecondBinding;


public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Usa ViewBinding para inflar la vista
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configura el WebView
        assert binding.wvContainer != null;
        binding.wvContainer.getSettings().setJavaScriptEnabled(true);
        binding.wvContainer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView web, WebResourceRequest request) {
                // Permitir que el WebView maneje los enlaces internos
                return false;
            }
        });

        // Carga la URL del producto en el WebView
        binding.wvContainer.loadUrl("https://www.eltiempo.com/noticias/deadpool-3");

        // Configura el botón para abrir el sitio web
        assert binding.buttonShowWeb != null;
        binding.buttonShowWeb.setOnClickListener(v -> {
            // Muestra el WebView
            binding.wvContainer.setVisibility(View.VISIBLE);
            // Oculta el botón de abrir sitio web
            binding.buttonShowWeb.setVisibility(View.GONE);
            // Muestra el botón de regresar
            assert binding.buttonBackToActivity != null;
            binding.buttonBackToActivity.setVisibility(View.VISIBLE);
        });

        // Configura el botón para regresar a la actividad anterior
        assert binding.buttonBackToActivity != null;
        binding.buttonBackToActivity.setOnClickListener(v -> {
            // Oculta el WebView
            binding.wvContainer.setVisibility(View.GONE);
            // Muestra el botón de abrir sitio web
            binding.buttonShowWeb.setVisibility(View.VISIBLE);
            // Oculta el botón de regresar
            binding.buttonBackToActivity.setVisibility(View.GONE);
        });

        // Agrega el fragmento de video al contenedor
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.videoContainer.getId(), new VideoFragment())
                    .commit();
        }

        // Inicialmente, el WebView y el botón de regresar están ocultos
        binding.wvContainer.setVisibility(View.GONE);
        binding.buttonBackToActivity.setVisibility(View.GONE);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            @SuppressLint("UseCompatLoadingForDrawables") Drawable upArrow = getResources().getDrawable(R.drawable.arrow_back, null);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the device is in landscape mode
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Hide the WebView, buttons, and toolbar
            assert binding.wvContainer != null;
            binding.wvContainer.setVisibility(View.GONE);
            assert binding.buttonShowWeb != null;
            binding.buttonShowWeb.setVisibility(View.GONE);
            assert binding.toolbar != null;
            binding.toolbar.setVisibility(View.GONE);
            assert binding.buttonBackToActivity != null;
            binding.buttonBackToActivity.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Show the WebView, buttons, and toolbar in portrait mode
            assert binding.wvContainer != null;
            binding.wvContainer.setVisibility(View.VISIBLE);
            assert binding.buttonShowWeb != null;
            binding.buttonShowWeb.setVisibility(View.VISIBLE);
            assert binding.toolbar != null;
            binding.toolbar.setVisibility(View.VISIBLE);

            // Only show the "back" button if it was previously visible
            assert binding.buttonBackToActivity != null;
            if (binding.buttonBackToActivity.getVisibility() == View.VISIBLE) {
                binding.buttonBackToActivity.setVisibility(View.VISIBLE);
            }
        }
    }
}