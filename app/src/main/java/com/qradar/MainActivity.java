public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // you'll create this XML later

        // Overlay permission check
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);
        }

        // Start VPN
        findViewById(R.id.btnStart).setOnClickListener(v -> {
            Intent prepare = VpnService.prepare(this);
            if (prepare != null) {
                startActivityForResult(prepare, 5678);
            } else {
                startServices();
            }
        });
    }

    private void startServices() {
        startService(new Intent(this, RadarVpnService.class));
        startService(new Intent(this, OverlayService.class));
    }
}