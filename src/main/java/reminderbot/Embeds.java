package reminderbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;

public class Embeds {
    //class to build reminders embed
    public static MessageEmbed allreminders(ArrayList<String> list){
        //new embed builder
        EmbedBuilder builder = new EmbedBuilder();
        //new title
        builder.setTitle("Current Reminders");
        //add all strings in embed
        for(int i = 0; i<list.size()-1;i+=2){
            builder.addField(list.get(i),"Due:" + list.get(i+1),true);
        }
        return builder.build();
    }
}
