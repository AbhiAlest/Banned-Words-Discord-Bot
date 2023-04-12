import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot extends ListenerAdapter {

    private static final List<String> bannedWords = new ArrayList<>();

    public static void main(String[] args) throws LoginException, IOException {
        bannedWords.addAll(Files.readAllLines(Paths.get("bannedWords.txt")));
        JDABuilder.createDefault("DISCORD_BOT_TOKEN")
                .addEventListeners(new DiscordBot())
                .build();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        String message = event.getMessage().getContentRaw().toLowerCase();
        for (String bannedWord : bannedWords) {
            if (message.contains(bannedWord)) {
                event.getGuild().kick(event.getAuthor()).queue();
                //  event.getGuild().ban(event.getAuthor(), 0) for banning the user
                break;
            }
        }
    }
}
