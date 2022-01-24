package me.bongowole.bongobox.command

import me.bongowole.bongobox.instance
import me.bongowole.bongobox.util.Utils
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class BongoBoxCommand : CommandBase() {

    override fun getCommandName(): String {
        return "bongobox"
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return "§c/$commandName config §b打开设置 GUI\n" +
                "§c/$commandName verbose <on/off> §b开启或关闭输出调试信息\n"
    }

    override fun processCommand(sender: ICommandSender?, args: Array<String>) {
        if (args.isNotEmpty()) {
            if (args[0].equals("config", true)) {
                renderConfigGui()
            } else if (args.size == 2 && args[0].equals("verbose", true)) {
                val flag = if (args[1].equals("on", true)) true else if (args[1].equals("off", false)) false else null
                if (flag == null) {
                    Utils.chat("§c/$commandName verbose <on/off> §b开启或关闭输出调试信息")
                } else instance().verbose = flag
            } else Utils.chat(getCommandUsage(sender))
        } else renderConfigGui()
    }

    fun renderConfigGui() {
    }

}