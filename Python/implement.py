import discord

banned_words = []

with open('bannedWords.txt', 'r') as file:
    banned_words = file.read().splitlines()

client = discord.Client()

@client.event
async def on_ready():
    print(f'Logged in through {client.user}')

@client.event
async def on_message(message):
    if message.author.bot:
        return
    content = message.content.lower()
    for banned_word in banned_words:
        if banned_word in content:
            await message.author.kick()
            # await message.author.ban() for banning the user
            break

client.run('DISCORD_BOT_TOKEN')
