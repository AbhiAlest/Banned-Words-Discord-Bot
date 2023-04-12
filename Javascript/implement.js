const Discord = require('discord.js');
const client = new Discord.Client();
const fs = require('fs');
const bannedWords = [];

fs.readFile('bannedWords.txt', 'utf8', (err, data) => { //upload banned words through bannedWords.txt 
  if (err) {
    console.error(err);
    return;
  }
  bannedWords.push(...data.trim().split('\n'));
});

client.on('ready', () => {
  console.log(`Logged in through ${client.user.tag}!`);
});

client.on('message', (message) => {
  if (message.author.bot) return; // Ignore messages from bots
  if (bannedWords.some(word => message.content.toLowerCase().includes(word))) {
    message.guild.member(message.author).kick();
    //message.guild.member(message.author).ban() for banning the user
  }
});

client.login('DISCORD_BOT_TOKEN');
