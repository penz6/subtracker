package reminderbot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.Timer;


//we need the listener adapter to listen for events
public class Bot extends ListenerAdapter {
	//declare guild and api
	static Guild homelab;
	static JDA api;

	public static void main(String[] args) throws IOException {
		//fancy way to read string from file
		String content = Files.readString(Paths.get("token.txt"));
		// start the bot
		api = JDABuilder.createDefault(content).addEventListeners(new Bot()).build();
		//timing
		timing();
	}

	//wait until connected to webhook
	public void onReady(ReadyEvent e) {
		//define guild
		homelab = api.getGuildById("1065644044610441236");
		//call update commands (remove in prod, not needed, may rate limit)
		updateCommands();
	}
//update commands
	public static void updateCommands() {
		System.out.println("Updated commands");
		//calling the update command just for the guild that i need
		homelab.updateCommands().addCommands(Commands.slash("ping", "Tests if the bot is working"),
				Commands.slash("newsubscription", "Add a new subscription to track").
						addOption(OptionType.STRING, "name", "What to name the subscription")
						.addOption(OptionType.STRING, "date","Format the date as dd/MM/yyyy"),
				Commands.slash("deletesubscription","Remove a subscription")
						.addOption(OptionType.STRING,"name","the name of the subscription to remove"),
				Commands.slash("list","Lists events")).queue();
	}
//listening for the event of a command
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		//if the command equals ping
		if (event.getName().equals("ping")) {
			event.reply("pong").queue(); // reply immediately
		}
		else if(event.getName().equals("newsubscription")){
			//call add sub
			try {
				FileManagement.addSub(event.getOption("name").getAsString(),event.getOption("date").getAsString());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			event.reply("Done!").queue();
		}
		else if(event.getName().equals("deletesubscription")){
			try {
				FileManagement.removeSub(event.getOption("name").getAsString());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			event.reply("Done!").queue();
		}else if(event.getName().equals("list")){
			String list;
			try {
				event.replyEmbeds(Embeds.allreminders(FileManagement.listFile())).queue();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	//start timing
	public static void timing(){
		Timer dates = new Timer();
		dates.schedule(new CheckDate(), 86400000);
	}
	//when a date is due
	public static void remindUser(ArrayList<String> a){
		//var
		MessageChannel subsremind = homelab.getTextChannelsByName("subscriptions",true).get(0);
		subsremind.sendMessage("You have subsription(s) due!");
		for(String temp : a){
			//break point
			int end  = temp.indexOf("-");
			subsremind.sendMessage("The subscription with the name " +  temp.substring(0,end) + " is due today!");
		}
	}
	
}
