using Discord;
using Discord.WebSocket;
using System;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;

public class Program
{
    private static List<string> bannedWords = new List<string>();

    public static async Task Main(){
    
        bannedWords.AddRange(File.ReadAllLines("bannedWords.txt"));

        var client = new DiscordSocketClient();

        client.Log += Log;

        client.MessageReceived += MessageReceived;

        await client.LoginAsync(TokenType.Bot, "DISCORD_BOT_TOKEN");

        await client.StartAsync();

        await Task.Delay(-1);
    }

    private static Task Log(LogMessage message){
    
        Console.WriteLine(message.ToString());
        return Task.CompletedTask;
    }

    private static async Task MessageReceived(SocketMessage message){
    
        if (message.Author.IsBot){
        
            return;
        }
        string content = message.Content.ToLower();
        foreach (string bannedWord in bannedWords){
        
            if (content.Contains(bannedWord)){
            
                await (message.Author as SocketGuildUser).KickAsync();
                // await (message.Author as SocketGuildUser).BanAsync() for banning the user
                break;
            }
        }
    }
}
