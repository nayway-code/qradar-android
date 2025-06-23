public class PacketParser {
    public static String parse(byte[] packet, int length) {
        // Basic header check (for Albion protocol packet)
        if (length < 10) return null;

        int opcode = packet[0] & 0xFF;

        switch (opcode) {
            case 0x01:
                return "Player appeared";
            case 0x02:
                return "Mob detected";
            // Add more opcodes or pattern matches here
            default:
                return null;
        }
    }
}