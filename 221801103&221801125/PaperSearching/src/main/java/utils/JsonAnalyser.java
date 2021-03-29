package utils;

import dao.PaperDaoimpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pojo.Paper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonAnalyser
{
    HashMap<String,Integer> keywordsCount=new HashMap<>();

    public List<Paper> Analyse(String path,String theConference)
    {
        List<Paper> list=new ArrayList<Paper>();
        try
        {
            File file = new File(path);
            if (file.isFile())
            {
                return null;
            }
            else
            {
                File[] files = file.listFiles();
                for (int k=0;k<files.length;k++)
                {
                    Paper paper=new Paper();
                    String isbn;
                    String title;
                    String authors = "";
                    String keywords = "";
                    String theabstract;
                    String publishDate;
                    String conference;
                    String paperlink;

                    BufferedReader bufferedReader = new BufferedReader(new FileReader(files[k]));

                    String paperString = bufferedReader.readLine();
                    JSONObject jsonObject = JSONObject.fromObject(paperString.substring(0, paperString.length() - 1));

                    /*isbn = JSONObject.fromObject(
                            jsonObject.getJSONArray("isbn").get(0)).getString("value");*/

                    isbn=jsonObject.getString("articleNumber");
                    title = jsonObject.getString("title");

                    JSONArray authorArray = jsonObject.getJSONArray("authors");
                    for (int i = 0; i < authorArray.size(); i++)
                    {

                        authors += JSONObject.fromObject(
                                jsonObject.getJSONArray("authors").get(i)).getString("name") + "//";
                    }



                    if (jsonObject.containsKey("keywords"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("keywords");

                        JSONObject temp = JSONObject.fromObject(jsonArray.get(0));
                        String kwd = temp.getString("kwd");
                        JSONArray jsonArray1 = JSONArray.fromObject(kwd);
                        Object[] objects = jsonArray1.toArray();
                        for (Object keyword : objects)
                        {
                            keywords += keyword + "//";
                        }

                        addKeywordsCount(keywords);
                    }


                    if (jsonObject.containsKey("abstract"))
                    {
                        theabstract = jsonObject.getString("abstract");
                    }
                    else {
                        theabstract="";
                    }

                    if (jsonObject.containsKey("chronOrPublicationDate"))
                    {
                        publishDate = jsonObject.getString("chronOrPublicationDate");
                    }
                    else {
                        publishDate="";
                    }


                    conference = theConference;

                    paperlink=jsonObject.getString("doiLink");

                    paper.setIsbn(isbn);
                    paper.setTitle(title);
                    paper.setAuthors(authors);
                    paper.setKeywords(keywords);
                    paper.setTheabstract(theabstract);
                    paper.setPublishDate(publishDate);
                    paper.setConferrence(conference);
                    paper.setPaperlink(paperlink);

                    System.out.println(paper.toString());
                    list.add(paper);
                }

            }
            System.out.println("返回List");
            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }




    public List<Paper> Analyse1(String path,String theConference)
    {
        List<Paper> list=new ArrayList<Paper>();
        try
        {
            File file = new File(path);
            if (file.isFile())
            {
                return null;
            }
            else
            {
                File[] files = file.listFiles();
                for (int k=0;k<files.length;k++)
                {

                    Paper paper=new Paper();
                    String isbn;
                    String title;
                    String authors = "";
                    String keywords = "";
                    String theabstract;
                    String publishDate;
                    String conference;
                    String paperlink;

                    BufferedReader bufferedReader = new BufferedReader(new FileReader(files[k]));

                    String paperString="";
                    String temp;
                    while((temp = bufferedReader.readLine())!=null)
                    {
                        paperString+=temp;
                    }
                    System.out.println(paperString);
                    JSONObject jsonObject = JSONObject.fromObject(paperString);

                    /*isbn = JSONObject.fromObject(
                            jsonObject.getJSONArray("isbn").get(0)).getString("value");*/

                    isbn=Integer.toString(k);
                    title = jsonObject.getString("论文名称");


                    if (jsonObject.containsKey("关键词"))
                    {
                        String kwd = jsonObject.getString("关键词");
                        JSONArray jsonArray=JSONArray.fromObject(kwd);
                        Object[] objects = jsonArray.toArray();
                        for (Object keyword : objects) {

                            keywords += keyword + "//";
                        }
                    }

                    addKeywordsCount(keywords);

                    if (jsonObject.containsKey("摘要"))
                    {
                        theabstract = jsonObject.getString("摘要");
                    }
                    else {
                        theabstract="";
                    }

                    if (jsonObject.containsKey("发布时间"))
                    {
                        publishDate = jsonObject.getString("发布时间");
                    }
                    else {
                        publishDate="";
                    }


                    conference = theConference;

                    paperlink=jsonObject.getString("原文链接");

                    paper.setIsbn(isbn);
                    paper.setTitle(title);
                    paper.setAuthors(authors);
                    paper.setKeywords(keywords);
                    paper.setTheabstract(theabstract);
                    paper.setPublishDate(publishDate);
                    paper.setConferrence(conference);
                    paper.setPaperlink(paperlink);

                    System.out.println(paper.toString());
                    list.add(paper);

                }

            }
            System.out.println("返回List");
            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void addKeywordsCount(String keywords)
    {
        String[] str=keywords.split("//");
        for(String s:str)
        {
            if(!keywordsCount.containsKey(s))
            {
                keywordsCount.put(s,1);
            }
            else
            {
                keywordsCount.put(s,keywordsCount.get(s)+1);
            }
        }
    }

    public HashMap<String,Integer> retKeyCount()
    {
       return keywordsCount;
    }

    public static void main(String args[])
    {
        PaperDaoimpl paperDaoimpl=new PaperDaoimpl();
        JsonAnalyser jsonAnalyser=new JsonAnalyser();
        paperDaoimpl.PaperStorage(jsonAnalyser.Analyse(
                "C:\\Users\\韩小韬\\Desktop\\论文数据\\论文数据\\CVPR（2000年至2020年，6916篇）",
                "cvpr"));
        paperDaoimpl.PaperStorage(jsonAnalyser.Analyse(
                "C:\\Users\\韩小韬\\Desktop\\论文数据\\论文数据\\ICCV（2001年至2019年，3196篇）",
                "iccv"));
        paperDaoimpl.PaperStorage(jsonAnalyser.Analyse1(
                "C:\\Users\\韩小韬\\Desktop\\论文数据\\论文数据\\ECCV（2016至2020，3033份）",
                "eccv"));
        paperDaoimpl.InsertKeyword(jsonAnalyser.retKeyCount());
    }
}
