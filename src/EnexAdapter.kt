import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EnexAdapter {
    val exportData = "20190201T133249Z"
    val application = "Evernote/Windows"
    val version = "6.x"
    var mTitle = "标题"
    val mTextList = mutableListOf("")
    var createTime = "20190201T132943Z"
    var updatedTime = "20190201T133244Z"
    val author = "XXX@gmail.com"   // 印象笔记 账号 xxx@qq.com
    val source = "desktop.win"               //设备名
    val source_application = "yinxiang.win32" //系统名

    var AllData = ""

    val DIV = "<div>"
    val DIV_ = "</div>"
    val CONTENT = "<content><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">\n" +
            "<en-note>"
    val CONTENT_ = "</en-note>]]></content>"

    fun setText(title: String, text: String, time:Long) {
        mTitle = title
        updatedTime = formatTime(time)
        createTime = formatTime(time)
        mTextList.clear()
        val list = text.split('\n').map { DIV + it + DIV_ }
        mTextList.addAll(list)
    }

    private fun getContentText(): String {
        var contentText = CONTENT
        mTextList.forEach { contentText += it }
        contentText += CONTENT_
        return contentText
    }

    private fun getRootText(): String {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE en-export SYSTEM \"http://xml.evernote.com/pub/evernote-export2.dtd\">\n" +
                "<en-export export-date=\"$exportData\" application=\"$application\" version=\"$version\">\n"
    }

    private fun getTitleText(): String {
        return "<note><title>$mTitle</title>"
    }

    private fun getDeviceText(): String {
        return "<created>$createTime</created>\n" +
                "<updated>$updatedTime</updated>\n" +
                "<note-attributes>\n" +
                "<author>$author</author>\n" +
                "<source>$source</source>\n" +
                "<source-application>$source_application</source-application>\n" +
                "</note-attributes></note>"
    }

    fun getAllText() {
        AllData += getTitleText() + getContentText() + getDeviceText()
    }

    fun export(): String {
        return getRootText() + AllData + "</en-export>"
    }

     fun formatTime(time:Long):String{
        val sdr = SimpleDateFormat("yyyyMMdd HHmmss-");
        return sdr.format(Date(time)).replace(' ', 'T').replace('-', 'Z')
    }

}