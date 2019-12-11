 import ConnectedDao.*;
import ContentObjects.*;
import java.util.*;
import java.text.DecimalFormat;

public class Test {
	private static DaoFactory instance=DaoFactory.getInstance();//定义一个工厂实例
	public static void main(String[] args) {
		//*********************用户登录
		User user=new User();//存储用户信息
		boolean isUserGo=true;
		Scanner reader=new Scanner(System.in);
		System.out.println("-------------------------用户登录----------------------------");
		//循环直到用户填写正确信息后停止
		String userId;
		String pwd;
		String option;
		System.out.println("请问通过密码登录（1）还是通过手机验证码（2）登录？");	
		System.out.print("请输入选择数字：");	
		option=reader.nextLine();
		System.out.println();
		if(option.equals("1")) {
		 
			while(isUserGo) {		 
				System.out.print("请输入您的用户名（豆瓣账号/手机号/邮箱）：");		
				userId=reader.nextLine();		
				System.out.print("请输入您的密码：");		
				pwd=reader.nextLine();
				System.out.println("~~~~~~~~~~~~~~~正在登录~~~~~~~~~~~~~~");
				//***********调取DAO方法获取user				
				user=instance.getUserDAO().getUser(userId);
				//---------------------------
				//user返回信息，则用户输入的用户名存在的
				if(user!=null) {
					if(user.getPassword().equals(pwd)) {
						isUserGo=false;
						break;//登录成功！跳出循环
					}else {
						System.out.println("密码错误！请重新登录！");	
					}
				}else {
					System.out.println("用户名不存在！");	
					//返回继续循环
				}
		}}else if(option.equals("2")) {	
			while(isUserGo) {
				System.out.print("请输入您的手机号：");
				userId=reader.nextLine();
				//***************调取dao方法获得user信息			
				user=instance.getUserDAO().getUser(userId);
				//
				System.out.println("此处模拟一下验证码登录----");
				System.out.println("*****--------Ding！手机收到验证码：404------*****");
			    System.out.print("请输入手机验证码:");
			    String phonePwd=reader.nextLine();
			    if(user!=null) {
					if(!phonePwd.equals("404")) {				
						System.out.println("输入错误！进行重新发送------");
					}else {
						isUserGo=false;
						break;
					}
			    }else {
			    	System.out.println("----！用户名不存在！----");
			    	//返回继续循环
			    }
			}
		}
		System.out.println();
		//跳出循环，登录成功了，且信息已经记录在user内
		System.out.println("***************恭喜用户"+user.getLoginAccount()+"登录成功！**************");
		System.out.println();
		System.out.println();
		//开始电影查询
		ArrayList<Film> filmList=new ArrayList<Film>();//存放查询后得到的电影集
		isUserGo=true;
		while(isUserGo) {
		System.out.print("接下来请输入您要查询的电影名：");
		String filmName=reader.nextLine();
		System.out.println();
		System.out.println("..........正在查询..........");
		try {
			Thread.sleep(5000);//使程序等待5秒
		}catch(Exception e) {
			e.printStackTrace();
		}
		//****************调取DAO方法获得film信息
		filmList=instance.getFilmDAO().getFilmByName(filmName);
		//----------------此处可能查询后返回的filmList=null，需要进行判断才进行下一步
		if(filmList==null) {
			//List为空
			System.out.println("查询结果为空！请重新输入......");
		}else if(filmList!=null) {
			isUserGo=false;
			break;
		}}
		System.out.println();
		//输出部分电影信息，供用户选择具体电影，只能选择一个，
		//选择完后进入循环
		Iterator<Film> iFilm=filmList.iterator();
		int number=0;
		Film film=new Film();//存放真正进行操作的电影信息
		System.out.println("查询结果如下：");	
		while(iFilm.hasNext()) {
			System.out.println("-------"+number+"-------");
			film=iFilm.next();
			System.out.println("电影名称："+film.getFilmName());
			System.out.println("电影海报URL："+film.getPoster());
			System.out.println("导演："+film.getDirector());
			System.out.println("上映日期："+film.getRaleaseDate());
		    System.out.println("制片国家/地区："+film.getCountry()); 
			number++;			
		}
		System.out.println();
		System.out.println();
		System.out.print("请输入您选择的电影（头部数字）：");
		int num=reader.nextInt();
		reader.nextLine();//接受回车键
		System.out.println();
		int comtNum=0;
		film=filmList.get(num);
		int fid=film.getFilmID();//获得当前想查看的电影号
		//得到电影信息，开始主菜单
		isUserGo=true;
		while(isUserGo) {
			//输出主菜单
			printMainManu();
			System.out.println();
			System.out.print("您想要进入（输入首部数字）：");
			option=reader.nextLine();
			System.out.println();
			switch(option) {
			case "0":
				//打印全部Film信息
				film.print();
				System.out.println();
				try {
					Thread.sleep(3000);//等待3秒
				}catch(Exception e) {
					e.printStackTrace();
				}
				//打印Actor信息
				int[] actorsID=instance.getRoleDAO().getActorsID(fid);//一部电影所有参演演员的ID
				int actorsNum=actorsID.length;//查询得到的一部电影的参演演员数量
				int actorsIndex=0;
				List<Actor> actors=new ArrayList<Actor>();//存放一个电影的全部演员对象
				List<ActorAward> actorAwards=new ArrayList<ActorAward>();//存放根据一个演员ID查询得到的所有ActorAward对象信息
				System.out.println("-----------------参演演员--------------------");
				for(;actorsIndex<actorsNum;actorsIndex++) {
					Actor actor=new Actor();
					Role role=new Role();
					int aid=actorsID[actorsIndex];
					actor=instance.getActorDAO().getActor(aid);
					role=instance.getRoleDAO().getRole(aid,fid);
					System.out.println("序号："+(actorsIndex+1)+"\t"+actor.getActor_Name());
					if(role.getStatus() ==1) {
						System.out.println("\t饰演的角色："+role.getRoleName()+"\t主角");
					}
					else {
						System.out.println("\t饰演的角色："+role.getRoleName()+"\t配角");
					}
					actors.add(actor);
				}
				System.out.println("----------------------------------------");
				System.out.println();
				int choice_actor_index=0;
				String wetherSearch=null;
				boolean ifContinue=true;//标记是否继续查看演员信息
				System.out.print("请选择是否查看演员的详细信息（Y/N）：");
				wetherSearch=reader.nextLine();
				System.out.println();
				if(wetherSearch.equals("Y")||wetherSearch.equals("y")) {
					while(ifContinue) {
						System.out.print("请输入想查看的演员对应的序号（输入-1则不查看或结束查看演员详细信息）：");
						choice_actor_index=reader.nextInt();
						System.out.println();
						if(choice_actor_index!=-1) {
							Actor ac=new Actor();
							ac=actors.get(choice_actor_index-1);
							ac.print();	
							actorAwards=instance.getActorAwardDAO().getActorAward(ac.getActor_ID());//一个演员所获得的所有奖项
							System.out.println();
							if(actorAwards!=null) {
								instance.getActorAwardDAO().print(actorAwards);//打印一个演员获得的所有奖项信息
							}
						}else {
							ifContinue=false;
						}
						System.out.println();
						System.out.println();
					}
				}
				try {
					Thread.sleep(3000);//等待3秒
				}catch(Exception e) {
					e.printStackTrace();
				}
				//打印电影获奖信息
				System.out.println("-----------------电影获得的奖项----------------");
				instance.getAwardDAO().print(instance.getAwardDAO().getAward(fid));
				System.out.println();
				//-----------------------电影影评------------------------------------
				//先获取影评
				ArrayList<Comment> commentList=new ArrayList<Comment>();
				commentList=instance.getCommentDAO().getComment(fid);
			    Comment comment=new Comment();//存放影评
			    Iterator<Comment> iComment=commentList.iterator();  
			    double sum=0;
			    int count=0;//用于计数
			    while(iComment.hasNext()) {
					comment=iComment.next();
					sum=sum+comment.getMark()*2;
					count++;
			    }
			    double mark=sum/count;
			    DecimalFormat df = new DecimalFormat("0.0");
			    try {
					Thread.sleep(3000);//等待3秒
				}catch(Exception e) {
					e.printStackTrace();
				}
			    System.out.println("-----------------电影获得的评分---------------");
			    System.out.println("\t评分\t"+df.format(mark));
				//打印部分影评（6条）
				//逐个打印		
			    Iterator<Comment> iComment1=commentList.iterator();
				comtNum=commentList.size();
				System.out.println("------------------影评版块----------------------");
				count=0;
				while(iComment1.hasNext()) {
					try {
						Thread.sleep(3000);//等待3秒
					}catch(Exception e) {
						e.printStackTrace();
					}
					System.out.println();
					Comment comm1=new Comment();
					comm1=iComment1.next();
					System.out.println("------影评"+(count+1)+"-----");
					//打印出影评信息
					User comtUser=new User();
					comtUser=instance.getUserDAO().getUser(comm1.getUserId());
					System.out.println("用户昵称："+comtUser.getNickname());
					System.out.println("发布日期："+comm1.getSubTime());
					System.out.println("发布内容："+comm1.getContent());
					System.out.println("有用："+comm1.getBeLiked());
					count++;
					if(count==6) {
						break;
					}
				}
				System.out.println();
				System.out.print("请选择是否查看所有影评（Y/N）：");
				reader.nextLine();//接受前面输入数字之后的回车键
				option=reader.nextLine();
				System.out.println();
				if(option.equals("Y")||option.equals("y")) {
					System.out.println("-------------------所有影评-----------------");
					count=0;
					Iterator<Comment> iComment2=commentList.iterator();
					while(iComment2.hasNext()) {
						try {
							Thread.sleep(3000);//等待3秒
						}catch(Exception e) {
							e.printStackTrace();
						}
						System.out.println();
						Comment comm2=new Comment();
						comm2=iComment2.next();
						System.out.println("------影评"+(count+1)+"-----");
						//打印出影评信息
						User comtUser=new User();
						comtUser=instance.getUserDAO().getUser(comm2.getUserId());
						System.out.println("用户昵称："+comtUser.getNickname());
						System.out.println("发布日期："+comm2.getSubTime());
						System.out.println("发布内容："+comm2.getContent());
						System.out.println("有用："+comm2.getBeLiked());
						count++;	
					}
				}
				System.out.println();
				//打印部分帖子标题（4）
				//获取帖子
				ArrayList<Discussion> discussionListSimple=new ArrayList<Discussion>();
				ArrayList<Discussion> discussionListOne=new ArrayList<Discussion>();
				discussionListSimple=instance.getDiscussionDAO().getDiscussion(film.getFilmID(), 1);
				//打印出帖子信息
				Iterator<Discussion> iDiscussion=discussionListSimple.iterator();
				count=0;
				System.out.println("------------------讨论版块---------------------");
				while(iDiscussion.hasNext()) {
					try {
						Thread.sleep(3000);//等待3秒
					}catch(Exception e) {
						e.printStackTrace();
					}
					System.out.println();
					System.out.println("------讨论"+(count+1)+"-----");
					Discussion discussion=new Discussion();
					discussion=iDiscussion.next();
					System.out.println("讨论标题："+discussion.getTitle());
					//打印出发帖人的昵称需要先获取
					User discUser=new User();
					discUser=instance.getUserDAO().getUser(discussion.getUserId());					
					System.out.println("来自"+discUser.getNickname()+"\t"+"发布时间："+discussion.getSubTime());
					//获取楼层
					discussionListOne=instance.getDiscussionDAO().getDiscussionAllFloor(discussion.getDiscussionId());
					System.out.println("回应 "+discussionListOne.size());					
					count++;
					if(count==3) {//count从0开始计
						break;
					}
				}
				//打印完成
				System.out.println();
				System.out.print("请选择是否查看所有讨论（Y/N）：");
				option=reader.nextLine();
				System.out.println();
				if(option.equals("Y")||option.equals("y")) {
					System.out.println("-----------------所有讨论-------------------");
					count=0;
					Iterator<Discussion> iDiscussion1=discussionListSimple.iterator();
					while(iDiscussion1.hasNext()) {
						try {
							Thread.sleep(3000);//等待3秒
						}catch(Exception e) {
							e.printStackTrace();
						}
						System.out.println();
						System.out.println("------讨论"+(count+1)+"-----");
						Discussion discussion1=new Discussion();
						discussion1=iDiscussion1.next();
						System.out.println("讨论标题："+discussion1.getTitle());
						//打印出发帖人的昵称需要先获取
						User discUser=new User();
						discUser=instance.getUserDAO().getUser(discussion1.getUserId());					
						System.out.println("来自"+discUser.getNickname()+"\t"+"发布时间"+discussion1.getSubTime());
						//获取楼层
						discussionListOne=instance.getDiscussionDAO().getDiscussionAllFloor(discussion1.getDiscussionId());
						System.out.println("回应 "+discussionListOne.size());						
						count++;
					}
					
				}
				break;
			case "1":
				 ArrayList<Discussion> oneDisc=new ArrayList<Discussion>();
				 int discId;
				 System.out.println("-------------------查看帖子-----------------");
				 System.out.print("请输入要查看的帖子ID：");
                 discId=reader.nextInt();
                 reader.nextLine();
				 oneDisc=instance.getDiscussionDAO().getDiscussionAllFloor(discId);
				 Iterator<Discussion> iDiscussion2=oneDisc.iterator();
				 Discussion disc=new Discussion();
				 while(iDiscussion2.hasNext()) {
					 try {
							Thread.sleep(2000);//等待2秒
						}catch(Exception e) {
							e.printStackTrace();
						}
					 disc=iDiscussion2.next();
					 System.out.println("");
					 User discUser=new User();
					 discUser=instance.getUserDAO().getUser(disc.getUserId());					
					 System.out.println("用户："+discUser.getNickname());
					 System.out.println("发布内容："+disc.getContent());
					 System.out.println("楼层："+disc.getFloor());
					 System.out.println("回复楼层："+disc.getReplyFloor());
					 System.out.println("发布时间："+disc.getSubTime());
					 System.out.println("楼层："+disc.getFloor());
					 System.out.println("被赞："+disc.getBeLiked());
				 }
				break;
			case "2":
				//标记电影,给电影打标签
				//先打印标记次数最多的前10个标签
				System.out.println("---------------为电影打标签(#^.^#)----------------");
				List<Label> lbs=new ArrayList<Label>();
				lbs=instance.getLabelDAO().getLabels(fid);
				boolean continueMark=true;//标记是否打标签
				String[] labels=new String[10];//记录打的所有标签名
				int label_choice=0;
				int label_count=0;
				int label_index=0;//标签数组下标
				while(continueMark) {
					instance.getLabelDAO().printLabelsName(lbs);//打印标签
					System.out.print("请输入想打的标签的序号（输入-1则结束打标签）：");
					label_choice=reader.nextInt();
					if(label_choice!=-1) {
						label_count=lbs.get(label_choice).getCount();//获取标签当前的标记次数
						labels[label_index]=lbs.get(label_choice).getLabelName();
						label_index++;
						//更改此标签的标记次数
						label_count=label_count+1;
						instance.getLabelDAO().updateLabelCount(fid, label_count);
					}else {
						continueMark=false;//结束打标签
					}
				}
				//把所有标签都放在一个String里
				String all_label=null;
				int mark_label_size=labels.length;
				for(int i=0;i<mark_label_size;i++) {
					if(i==mark_label_size-1) {
						all_label+=labels[i];
					}
					else {
						all_label+=labels[i]+"/";
					}
				}
				//新建一个电影标记记录对象
				Record record=new Record();
				record.setUserID(user.getUserID());
				record.setFid(fid);
				record.setTag(all_label);
				System.out.println("记录一下想看这部电影的理由~");
				String reason=null;
				boolean isReasonBlank=true;//记录输入的内容是否为空
				reader.nextLine();//接受前面输入数字之后的回车键
				while(isReasonBlank) {
					reason=reader.nextLine();
					if(reason!=null) {
						isReasonBlank=false;
						record.setReason(reason);
						instance.getRecordDAO().insertRecord(record);
						System.out.println("~~~~~~~~标记成功~~~~~~~~");
					}
					else {
						isReasonBlank=true;
						System.out.println("未输入理由，请输入~~~");
					}
				}
				System.out.println();
				break;
			case "3":
				//添加影评
				//此处是添加在comment表里
				Comment comt=new Comment();
				comt.setUserId(user.getUserID());
				comt.setFilmId(film.getFilmID());
				comt.setCommentId(comtNum+1);
				System.out.println("请输入评分");
				comt.setMark(reader.nextInt());
				reader.nextLine();//接受回车键
				System.out.println("请输入内容：");
				comt.setContent(reader.nextLine());
				System.out.println("请输入时间：");
				comt.setSubTime(reader.nextLine());
				System.out.println("请输入终端设备：");
				comt.setTerminal(reader.nextLine());
				comt.setBeLiked(0);
				instance.getCommentDAO().insertComment(comt);
				System.out.println();
				//-*********
				break;
			case "4":
				//参与讨论
				Discussion disc1=new Discussion();
				System.out.println("是否创建新帖子？（Y/N）");
				option=reader.nextLine();
				if(option.equals("Y")||option.equals("y")) {
				System.out.println("请输入帖子ID：");
				disc1.setDiscussionId(reader.nextInt());	
				disc1.setUserId(user.getUserID());
				disc1.setFilmId(film.getFilmID());
				disc1.setFloor(1);
				disc1.setReplyFloor(-1);
				reader.nextLine();//接受回车键
				System.out.println("请输入标题：");
				disc1.setTitle(reader.nextLine());
				System.out.println("请输入讨论内容：");
				disc1.setContent(reader.nextLine());
				System.out.println("请输入时间：");
				disc1.setSubTime(reader.nextLine());
				disc1.setBeLiked(0);
				}else if(option.equals("N")||option.equals("n")){
					System.out.println("请输入参与讨论的帖子ID：");
					disc1.setDiscussionId(reader.nextInt());
					disc1.setUserId(user.getUserID());
					disc1.setFilmId(film.getFilmID());
					disc1.setUserId(user.getUserID());
					System.out.println("请输入回复楼层（若无则为-1)：");
					disc1.setReplyFloor(reader.nextInt());
					
					//获得当前帖子ID所有楼层数
					ArrayList<Discussion> oneDisc1=new ArrayList<Discussion>();
					oneDisc1=instance.getDiscussionDAO().getDiscussionAllFloor(disc1.getDiscussionId());
					int floorNum=oneDisc1.size()+1;
					disc1.setFloor(floorNum);
					//获取标题
					Discussion discTemp=new Discussion();
					discTemp=oneDisc1.get(0);
					disc1.setTitle(discTemp.getTitle());
					reader.nextLine();//接受回车键
					System.out.println("请输入讨论内容：");
					disc1.setContent(reader.nextLine());
					System.out.println("请输入时间：");
					disc1.setSubTime(reader.nextLine());
					disc1.setBeLiked(0);
				}
				System.out.println();
				break;
			case "5":
				isUserGo=false;//这里实现则需要再在外层加一个大循环，暂且就不实现了
				break;
			default:
				System.out.println("错误的选择！");
				break;
			}
			
		}
	}
	//打印主菜单
	public static void printMainManu() {
		System.out.println("-------------------主菜单-------------------");
		System.out.println("请务必先进入0！");
		System.out.println("0.查看该电影全部信息");	
		System.out.println("1.查看指定帖子");
		System.out.println("2.标记该电影");
		System.out.println("3.添加影评");
		System.out.println("4.参与讨论");	
		System.out.println("5.返回，继续查询电影......");
    }

}
