 import ConnectedDao.*;
import ContentObjects.*;
import java.util.*;
import java.text.DecimalFormat;

public class Test {
	private static DaoFactory instance=DaoFactory.getInstance();//����һ������ʵ��
	public static void main(String[] args) {
		//*********************�û���¼
		User user=new User();//�洢�û���Ϣ
		boolean isUserGo=true;
		Scanner reader=new Scanner(System.in);
		System.out.println("-------------------------�û���¼----------------------------");
		//ѭ��ֱ���û���д��ȷ��Ϣ��ֹͣ
		String userId;
		String pwd;
		String option;
		System.out.println("����ͨ�������¼��1������ͨ���ֻ���֤�루2����¼��");	
		System.out.print("������ѡ�����֣�");	
		option=reader.nextLine();
		System.out.println();
		if(option.equals("1")) {
		 
			while(isUserGo) {		 
				System.out.print("�����������û����������˺�/�ֻ���/���䣩��");		
				userId=reader.nextLine();		
				System.out.print("�������������룺");		
				pwd=reader.nextLine();
				System.out.println("~~~~~~~~~~~~~~~���ڵ�¼~~~~~~~~~~~~~~");
				//***********��ȡDAO������ȡuser				
				user=instance.getUserDAO().getUser(userId);
				//---------------------------
				//user������Ϣ�����û�������û������ڵ�
				if(user!=null) {
					if(user.getPassword().equals(pwd)) {
						isUserGo=false;
						break;//��¼�ɹ�������ѭ��
					}else {
						System.out.println("������������µ�¼��");	
					}
				}else {
					System.out.println("�û��������ڣ�");	
					//���ؼ���ѭ��
				}
		}}else if(option.equals("2")) {	
			while(isUserGo) {
				System.out.print("�����������ֻ��ţ�");
				userId=reader.nextLine();
				//***************��ȡdao�������user��Ϣ			
				user=instance.getUserDAO().getUser(userId);
				//
				System.out.println("�˴�ģ��һ����֤���¼----");
				System.out.println("*****--------Ding���ֻ��յ���֤�룺404------*****");
			    System.out.print("�������ֻ���֤��:");
			    String phonePwd=reader.nextLine();
			    if(user!=null) {
					if(!phonePwd.equals("404")) {				
						System.out.println("������󣡽������·���------");
					}else {
						isUserGo=false;
						break;
					}
			    }else {
			    	System.out.println("----���û��������ڣ�----");
			    	//���ؼ���ѭ��
			    }
			}
		}
		System.out.println();
		//����ѭ������¼�ɹ��ˣ�����Ϣ�Ѿ���¼��user��
		System.out.println("***************��ϲ�û�"+user.getLoginAccount()+"��¼�ɹ���**************");
		System.out.println();
		System.out.println();
		//��ʼ��Ӱ��ѯ
		ArrayList<Film> filmList=new ArrayList<Film>();//��Ų�ѯ��õ��ĵ�Ӱ��
		isUserGo=true;
		while(isUserGo) {
		System.out.print("��������������Ҫ��ѯ�ĵ�Ӱ����");
		String filmName=reader.nextLine();
		System.out.println();
		System.out.println("..........���ڲ�ѯ..........");
		try {
			Thread.sleep(5000);//ʹ����ȴ�5��
		}catch(Exception e) {
			e.printStackTrace();
		}
		//****************��ȡDAO�������film��Ϣ
		filmList=instance.getFilmDAO().getFilmByName(filmName);
		//----------------�˴����ܲ�ѯ�󷵻ص�filmList=null����Ҫ�����жϲŽ�����һ��
		if(filmList==null) {
			//ListΪ��
			System.out.println("��ѯ���Ϊ�գ�����������......");
		}else if(filmList!=null) {
			isUserGo=false;
			break;
		}}
		System.out.println();
		//������ֵ�Ӱ��Ϣ�����û�ѡ������Ӱ��ֻ��ѡ��һ����
		//ѡ��������ѭ��
		Iterator<Film> iFilm=filmList.iterator();
		int number=0;
		Film film=new Film();//����������в����ĵ�Ӱ��Ϣ
		System.out.println("��ѯ������£�");	
		while(iFilm.hasNext()) {
			System.out.println("-------"+number+"-------");
			film=iFilm.next();
			System.out.println("��Ӱ���ƣ�"+film.getFilmName());
			System.out.println("��Ӱ����URL��"+film.getPoster());
			System.out.println("���ݣ�"+film.getDirector());
			System.out.println("��ӳ���ڣ�"+film.getRaleaseDate());
		    System.out.println("��Ƭ����/������"+film.getCountry()); 
			number++;			
		}
		System.out.println();
		System.out.println();
		System.out.print("��������ѡ��ĵ�Ӱ��ͷ�����֣���");
		int num=reader.nextInt();
		reader.nextLine();//���ܻس���
		System.out.println();
		int comtNum=0;
		film=filmList.get(num);
		int fid=film.getFilmID();//��õ�ǰ��鿴�ĵ�Ӱ��
		//�õ���Ӱ��Ϣ����ʼ���˵�
		isUserGo=true;
		while(isUserGo) {
			//������˵�
			printMainManu();
			System.out.println();
			System.out.print("����Ҫ���루�����ײ����֣���");
			option=reader.nextLine();
			System.out.println();
			switch(option) {
			case "0":
				//��ӡȫ��Film��Ϣ
				film.print();
				System.out.println();
				try {
					Thread.sleep(3000);//�ȴ�3��
				}catch(Exception e) {
					e.printStackTrace();
				}
				//��ӡActor��Ϣ
				int[] actorsID=instance.getRoleDAO().getActorsID(fid);//һ����Ӱ���в�����Ա��ID
				int actorsNum=actorsID.length;//��ѯ�õ���һ����Ӱ�Ĳ�����Ա����
				int actorsIndex=0;
				List<Actor> actors=new ArrayList<Actor>();//���һ����Ӱ��ȫ����Ա����
				List<ActorAward> actorAwards=new ArrayList<ActorAward>();//��Ÿ���һ����ԱID��ѯ�õ�������ActorAward������Ϣ
				System.out.println("-----------------������Ա--------------------");
				for(;actorsIndex<actorsNum;actorsIndex++) {
					Actor actor=new Actor();
					Role role=new Role();
					int aid=actorsID[actorsIndex];
					actor=instance.getActorDAO().getActor(aid);
					role=instance.getRoleDAO().getRole(aid,fid);
					System.out.println("��ţ�"+(actorsIndex+1)+"\t"+actor.getActor_Name());
					if(role.getStatus() ==1) {
						System.out.println("\t���ݵĽ�ɫ��"+role.getRoleName()+"\t����");
					}
					else {
						System.out.println("\t���ݵĽ�ɫ��"+role.getRoleName()+"\t���");
					}
					actors.add(actor);
				}
				System.out.println("----------------------------------------");
				System.out.println();
				int choice_actor_index=0;
				String wetherSearch=null;
				boolean ifContinue=true;//����Ƿ�����鿴��Ա��Ϣ
				System.out.print("��ѡ���Ƿ�鿴��Ա����ϸ��Ϣ��Y/N����");
				wetherSearch=reader.nextLine();
				System.out.println();
				if(wetherSearch.equals("Y")||wetherSearch.equals("y")) {
					while(ifContinue) {
						System.out.print("��������鿴����Ա��Ӧ����ţ�����-1�򲻲鿴������鿴��Ա��ϸ��Ϣ����");
						choice_actor_index=reader.nextInt();
						System.out.println();
						if(choice_actor_index!=-1) {
							Actor ac=new Actor();
							ac=actors.get(choice_actor_index-1);
							ac.print();	
							actorAwards=instance.getActorAwardDAO().getActorAward(ac.getActor_ID());//һ����Ա����õ����н���
							System.out.println();
							if(actorAwards!=null) {
								instance.getActorAwardDAO().print(actorAwards);//��ӡһ����Ա��õ����н�����Ϣ
							}
						}else {
							ifContinue=false;
						}
						System.out.println();
						System.out.println();
					}
				}
				try {
					Thread.sleep(3000);//�ȴ�3��
				}catch(Exception e) {
					e.printStackTrace();
				}
				//��ӡ��Ӱ����Ϣ
				System.out.println("-----------------��Ӱ��õĽ���----------------");
				instance.getAwardDAO().print(instance.getAwardDAO().getAward(fid));
				System.out.println();
				//-----------------------��ӰӰ��------------------------------------
				//�Ȼ�ȡӰ��
				ArrayList<Comment> commentList=new ArrayList<Comment>();
				commentList=instance.getCommentDAO().getComment(fid);
			    Comment comment=new Comment();//���Ӱ��
			    Iterator<Comment> iComment=commentList.iterator();  
			    double sum=0;
			    int count=0;//���ڼ���
			    while(iComment.hasNext()) {
					comment=iComment.next();
					sum=sum+comment.getMark()*2;
					count++;
			    }
			    double mark=sum/count;
			    DecimalFormat df = new DecimalFormat("0.0");
			    try {
					Thread.sleep(3000);//�ȴ�3��
				}catch(Exception e) {
					e.printStackTrace();
				}
			    System.out.println("-----------------��Ӱ��õ�����---------------");
			    System.out.println("\t����\t"+df.format(mark));
				//��ӡ����Ӱ����6����
				//�����ӡ		
			    Iterator<Comment> iComment1=commentList.iterator();
				comtNum=commentList.size();
				System.out.println("------------------Ӱ�����----------------------");
				count=0;
				while(iComment1.hasNext()) {
					try {
						Thread.sleep(3000);//�ȴ�3��
					}catch(Exception e) {
						e.printStackTrace();
					}
					System.out.println();
					Comment comm1=new Comment();
					comm1=iComment1.next();
					System.out.println("------Ӱ��"+(count+1)+"-----");
					//��ӡ��Ӱ����Ϣ
					User comtUser=new User();
					comtUser=instance.getUserDAO().getUser(comm1.getUserId());
					System.out.println("�û��ǳƣ�"+comtUser.getNickname());
					System.out.println("�������ڣ�"+comm1.getSubTime());
					System.out.println("�������ݣ�"+comm1.getContent());
					System.out.println("���ã�"+comm1.getBeLiked());
					count++;
					if(count==6) {
						break;
					}
				}
				System.out.println();
				System.out.print("��ѡ���Ƿ�鿴����Ӱ����Y/N����");
				reader.nextLine();//����ǰ����������֮��Ļس���
				option=reader.nextLine();
				System.out.println();
				if(option.equals("Y")||option.equals("y")) {
					System.out.println("-------------------����Ӱ��-----------------");
					count=0;
					Iterator<Comment> iComment2=commentList.iterator();
					while(iComment2.hasNext()) {
						try {
							Thread.sleep(3000);//�ȴ�3��
						}catch(Exception e) {
							e.printStackTrace();
						}
						System.out.println();
						Comment comm2=new Comment();
						comm2=iComment2.next();
						System.out.println("------Ӱ��"+(count+1)+"-----");
						//��ӡ��Ӱ����Ϣ
						User comtUser=new User();
						comtUser=instance.getUserDAO().getUser(comm2.getUserId());
						System.out.println("�û��ǳƣ�"+comtUser.getNickname());
						System.out.println("�������ڣ�"+comm2.getSubTime());
						System.out.println("�������ݣ�"+comm2.getContent());
						System.out.println("���ã�"+comm2.getBeLiked());
						count++;	
					}
				}
				System.out.println();
				//��ӡ�������ӱ��⣨4��
				//��ȡ����
				ArrayList<Discussion> discussionListSimple=new ArrayList<Discussion>();
				ArrayList<Discussion> discussionListOne=new ArrayList<Discussion>();
				discussionListSimple=instance.getDiscussionDAO().getDiscussion(film.getFilmID(), 1);
				//��ӡ��������Ϣ
				Iterator<Discussion> iDiscussion=discussionListSimple.iterator();
				count=0;
				System.out.println("------------------���۰��---------------------");
				while(iDiscussion.hasNext()) {
					try {
						Thread.sleep(3000);//�ȴ�3��
					}catch(Exception e) {
						e.printStackTrace();
					}
					System.out.println();
					System.out.println("------����"+(count+1)+"-----");
					Discussion discussion=new Discussion();
					discussion=iDiscussion.next();
					System.out.println("���۱��⣺"+discussion.getTitle());
					//��ӡ�������˵��ǳ���Ҫ�Ȼ�ȡ
					User discUser=new User();
					discUser=instance.getUserDAO().getUser(discussion.getUserId());					
					System.out.println("����"+discUser.getNickname()+"\t"+"����ʱ�䣺"+discussion.getSubTime());
					//��ȡ¥��
					discussionListOne=instance.getDiscussionDAO().getDiscussionAllFloor(discussion.getDiscussionId());
					System.out.println("��Ӧ "+discussionListOne.size());					
					count++;
					if(count==3) {//count��0��ʼ��
						break;
					}
				}
				//��ӡ���
				System.out.println();
				System.out.print("��ѡ���Ƿ�鿴�������ۣ�Y/N����");
				option=reader.nextLine();
				System.out.println();
				if(option.equals("Y")||option.equals("y")) {
					System.out.println("-----------------��������-------------------");
					count=0;
					Iterator<Discussion> iDiscussion1=discussionListSimple.iterator();
					while(iDiscussion1.hasNext()) {
						try {
							Thread.sleep(3000);//�ȴ�3��
						}catch(Exception e) {
							e.printStackTrace();
						}
						System.out.println();
						System.out.println("------����"+(count+1)+"-----");
						Discussion discussion1=new Discussion();
						discussion1=iDiscussion1.next();
						System.out.println("���۱��⣺"+discussion1.getTitle());
						//��ӡ�������˵��ǳ���Ҫ�Ȼ�ȡ
						User discUser=new User();
						discUser=instance.getUserDAO().getUser(discussion1.getUserId());					
						System.out.println("����"+discUser.getNickname()+"\t"+"����ʱ��"+discussion1.getSubTime());
						//��ȡ¥��
						discussionListOne=instance.getDiscussionDAO().getDiscussionAllFloor(discussion1.getDiscussionId());
						System.out.println("��Ӧ "+discussionListOne.size());						
						count++;
					}
					
				}
				break;
			case "1":
				 ArrayList<Discussion> oneDisc=new ArrayList<Discussion>();
				 int discId;
				 System.out.println("-------------------�鿴����-----------------");
				 System.out.print("������Ҫ�鿴������ID��");
                 discId=reader.nextInt();
                 reader.nextLine();
				 oneDisc=instance.getDiscussionDAO().getDiscussionAllFloor(discId);
				 Iterator<Discussion> iDiscussion2=oneDisc.iterator();
				 Discussion disc=new Discussion();
				 while(iDiscussion2.hasNext()) {
					 try {
							Thread.sleep(2000);//�ȴ�2��
						}catch(Exception e) {
							e.printStackTrace();
						}
					 disc=iDiscussion2.next();
					 System.out.println("");
					 User discUser=new User();
					 discUser=instance.getUserDAO().getUser(disc.getUserId());					
					 System.out.println("�û���"+discUser.getNickname());
					 System.out.println("�������ݣ�"+disc.getContent());
					 System.out.println("¥�㣺"+disc.getFloor());
					 System.out.println("�ظ�¥�㣺"+disc.getReplyFloor());
					 System.out.println("����ʱ�䣺"+disc.getSubTime());
					 System.out.println("¥�㣺"+disc.getFloor());
					 System.out.println("���ޣ�"+disc.getBeLiked());
				 }
				break;
			case "2":
				//��ǵ�Ӱ,����Ӱ���ǩ
				//�ȴ�ӡ��Ǵ�������ǰ10����ǩ
				System.out.println("---------------Ϊ��Ӱ���ǩ(#^.^#)----------------");
				List<Label> lbs=new ArrayList<Label>();
				lbs=instance.getLabelDAO().getLabels(fid);
				boolean continueMark=true;//����Ƿ���ǩ
				String[] labels=new String[10];//��¼������б�ǩ��
				int label_choice=0;
				int label_count=0;
				int label_index=0;//��ǩ�����±�
				while(continueMark) {
					instance.getLabelDAO().printLabelsName(lbs);//��ӡ��ǩ
					System.out.print("���������ı�ǩ����ţ�����-1��������ǩ����");
					label_choice=reader.nextInt();
					if(label_choice!=-1) {
						label_count=lbs.get(label_choice).getCount();//��ȡ��ǩ��ǰ�ı�Ǵ���
						labels[label_index]=lbs.get(label_choice).getLabelName();
						label_index++;
						//���Ĵ˱�ǩ�ı�Ǵ���
						label_count=label_count+1;
						instance.getLabelDAO().updateLabelCount(fid, label_count);
					}else {
						continueMark=false;//�������ǩ
					}
				}
				//�����б�ǩ������һ��String��
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
				//�½�һ����Ӱ��Ǽ�¼����
				Record record=new Record();
				record.setUserID(user.getUserID());
				record.setFid(fid);
				record.setTag(all_label);
				System.out.println("��¼һ���뿴�ⲿ��Ӱ������~");
				String reason=null;
				boolean isReasonBlank=true;//��¼����������Ƿ�Ϊ��
				reader.nextLine();//����ǰ����������֮��Ļس���
				while(isReasonBlank) {
					reason=reader.nextLine();
					if(reason!=null) {
						isReasonBlank=false;
						record.setReason(reason);
						instance.getRecordDAO().insertRecord(record);
						System.out.println("~~~~~~~~��ǳɹ�~~~~~~~~");
					}
					else {
						isReasonBlank=true;
						System.out.println("δ�������ɣ�������~~~");
					}
				}
				System.out.println();
				break;
			case "3":
				//���Ӱ��
				//�˴��������comment����
				Comment comt=new Comment();
				comt.setUserId(user.getUserID());
				comt.setFilmId(film.getFilmID());
				comt.setCommentId(comtNum+1);
				System.out.println("����������");
				comt.setMark(reader.nextInt());
				reader.nextLine();//���ܻس���
				System.out.println("���������ݣ�");
				comt.setContent(reader.nextLine());
				System.out.println("������ʱ�䣺");
				comt.setSubTime(reader.nextLine());
				System.out.println("�������ն��豸��");
				comt.setTerminal(reader.nextLine());
				comt.setBeLiked(0);
				instance.getCommentDAO().insertComment(comt);
				System.out.println();
				//-*********
				break;
			case "4":
				//��������
				Discussion disc1=new Discussion();
				System.out.println("�Ƿ񴴽������ӣ���Y/N��");
				option=reader.nextLine();
				if(option.equals("Y")||option.equals("y")) {
				System.out.println("����������ID��");
				disc1.setDiscussionId(reader.nextInt());	
				disc1.setUserId(user.getUserID());
				disc1.setFilmId(film.getFilmID());
				disc1.setFloor(1);
				disc1.setReplyFloor(-1);
				reader.nextLine();//���ܻس���
				System.out.println("��������⣺");
				disc1.setTitle(reader.nextLine());
				System.out.println("�������������ݣ�");
				disc1.setContent(reader.nextLine());
				System.out.println("������ʱ�䣺");
				disc1.setSubTime(reader.nextLine());
				disc1.setBeLiked(0);
				}else if(option.equals("N")||option.equals("n")){
					System.out.println("������������۵�����ID��");
					disc1.setDiscussionId(reader.nextInt());
					disc1.setUserId(user.getUserID());
					disc1.setFilmId(film.getFilmID());
					disc1.setUserId(user.getUserID());
					System.out.println("������ظ�¥�㣨������Ϊ-1)��");
					disc1.setReplyFloor(reader.nextInt());
					
					//��õ�ǰ����ID����¥����
					ArrayList<Discussion> oneDisc1=new ArrayList<Discussion>();
					oneDisc1=instance.getDiscussionDAO().getDiscussionAllFloor(disc1.getDiscussionId());
					int floorNum=oneDisc1.size()+1;
					disc1.setFloor(floorNum);
					//��ȡ����
					Discussion discTemp=new Discussion();
					discTemp=oneDisc1.get(0);
					disc1.setTitle(discTemp.getTitle());
					reader.nextLine();//���ܻس���
					System.out.println("�������������ݣ�");
					disc1.setContent(reader.nextLine());
					System.out.println("������ʱ�䣺");
					disc1.setSubTime(reader.nextLine());
					disc1.setBeLiked(0);
				}
				System.out.println();
				break;
			case "5":
				isUserGo=false;//����ʵ������Ҫ��������һ����ѭ�������ҾͲ�ʵ����
				break;
			default:
				System.out.println("�����ѡ��");
				break;
			}
			
		}
	}
	//��ӡ���˵�
	public static void printMainManu() {
		System.out.println("-------------------���˵�-------------------");
		System.out.println("������Ƚ���0��");
		System.out.println("0.�鿴�õ�Ӱȫ����Ϣ");	
		System.out.println("1.�鿴ָ������");
		System.out.println("2.��Ǹõ�Ӱ");
		System.out.println("3.���Ӱ��");
		System.out.println("4.��������");	
		System.out.println("5.���أ�������ѯ��Ӱ......");
    }

}
