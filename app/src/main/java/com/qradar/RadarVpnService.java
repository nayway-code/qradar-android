public class RadarVpnService extends VpnService {
    private ParcelFileDescriptor vpnInterface;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        establishVpn();
        new Thread(this::capturePackets).start();
        return START_STICKY;
    }

    private void establishVpn() {
        Builder builder = new Builder();
        builder.setSession("QRadarVPN")
               .addAddress("10.0.0.2", 32)
               .addDnsServer("8.8.8.8")
               .addRoute("0.0.0.0", 0);
        vpnInterface = builder.establish();
    }

    private void capturePackets() {
        FileInputStream in = new FileInputStream(vpnInterface.getFileDescriptor());
        byte[] packet = new byte[32767];

        while (true) {
            int length = in.read(packet);
            if (length > 0) {
                // Call your PacketParser
                String result = PacketParser.parse(packet, length);
                if (result != null) {
                    // Send to overlay via broadcast or service binding
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (vpnInterface != null) vpnInterface.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}