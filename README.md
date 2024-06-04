# 💡 CartelNameFix Java Minecraft Plugin

## 🛠️ What Does This Plugin Do?

This plugin is designed to prevent players from exploiting permission or account bugs to disrupt the economic or authority balance of the server. When players join the server with the name "Cartel," this name is recorded in a MySQL database. After being registered, permission matching is done in the tables, associating the name with the permissions of the player. Some plugins, due to certain bugs, mistakenly apply the permissions of the "Cartel" account to anyone trying to enter the game with the name "cartel." This system solves that issue precisely.

## 🚀 How Does This Plugin Solve the Problem?

The issue arises from various plugins or vulnerabilities in older Minecraft versions. This plugin addresses it by creating an SQL database to control connection requests of players joining the game based on the data in the SQL. If such an account does not exist in the plugin's SQL, it will be created, and default permissions will be assigned to the newly created accounts. Subsequently, if administrators assign you administrative permissions in the game, it will be updated in the database as well. In short, if there's an account named "Cartel," you can no longer create an account with the name "cartel."

## 🛠️ What's Needed for Installation?

Currently, it doesn't depend on any other plugin, so it will work by simply placing the CartelNameFix.jar file into the ./plugins directory. However, for clarification:

- Navigate to the ./plugins directory of your server and drag the CartelNameFix.jar file into it.
- Start your server and monitor the console. You'll see informative messages indicating the plugin has been loaded.
- If the plugin loads successfully, it will create a file and an associated database file. All name data for your server will be stored here.
- This way, you'll be free from all name bugs.

For further information, feel free to contact me. You can also support me in future projects by checking out my GitHub profile and reaching out to me through the provided channels.
