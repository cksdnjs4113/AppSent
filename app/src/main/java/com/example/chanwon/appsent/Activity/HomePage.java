package com.example.chanwon.appsent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chanwon.appsent.DAO.DatabaseHelper;
import com.example.chanwon.appsent.NavigationDrawer;
import com.example.chanwon.appsent.R;


public class HomePage extends ActionBarActivity {
    Button btnAnal, btnInsert;

    //DATABASE
    DatabaseHelper mydb;
    //DATABASE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //DATABASE
        mydb = new DatabaseHelper(this);
        //DATABASE

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAnal = (Button) findViewById(R.id.btnAnal);
        btnInsert = (Button) findViewById(R.id.buttonInsert);
        inserting();
        analyzing();


        NavigationDrawer drawerFragment = (NavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    public void analyzing() {
        btnAnal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HomePage.this, OverviewSentiment.class));
                    }
                }
        );
    }

    public void inserting() {
        btnInsert.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        mydb.insertData("5	Emerges as something rare , an issue movie that 's so honest and keenly observed that it does n't feel like one .", "positive", "fear", "2015-05-01");
                        mydb.insertData("6	The film provides some great insight into the neurotic mindset of all comics -- even those who have reached the absolute top of the game .", "positive", "happy", "2015-05-01");
                        mydb.insertData("7	Offers that rare combination of entertainment and education .", "positive", "happy", "2015-05-01");
                        mydb.insertData("8	Perhaps no picture ever made has more literally showed that the road to hell is paved with good intentions .", "negative", "sad", "2015-05-01");
                        mydb.insertData("9	Steers turns in a snappy screenplay that curls at the edges ; it 's so clever you want to hate it .", "positive", "anger", "2015-05-01");
                        mydb.insertData("10	But he somehow pulls it off .", "neutral", "happy", "2015-05-01");
                        mydb.insertData("11	Take Care of My Cat offers a refreshingly different slice of Asian cinema .", "positive", "fear", "2015-05-01");
                        mydb.insertData("12	This is a film well worth seeing , talking and singing heads and all .", "positive", "happy", "2015-05-01");
                        mydb.insertData("13	What really surprises about Wisegirls is its low-key quality and genuine tenderness .", "positive", "happy", "2015-05-01");
                        mydb.insertData("14	-LRB- Wendigo is -RRB- why we go to the cinema : to be fed through the eye , the heart , the mind .", "positive", "fear", "2015-05-01");
                        mydb.insertData("15	One of the greatest family-oriented , fantasy-adventure movies ever .", "positive", "happy", "2015-05-01");
                        mydb.insertData("16	Ultimately , it ponders the reasons we need stories so much .", "negative", "sad", "2015-05-01");
                        mydb.insertData("17	An utterly compelling ` who wrote it ' in which the reputation of the most famous author who ever lived comes into question .", "positive", "sad", "2015-05-01");
                        mydb.insertData("18	Illuminating if overly talky documentary .", "negative", "happy", "2015-05-01");
                        mydb.insertData("19	A masterpiece four years in the making .", "positive", "happy", "2015-05-01");
                        mydb.insertData("20	The movie 's ripe , enrapturing beauty will tempt those willing to probe its inscrutable mysteries .", "positive", "happy", "2015-05-01");
                        mydb.insertData("21	Offers a breath of the fresh air of true sophistication .", "positive", "happy", "2015-05-01");
                        mydb.insertData("22	A thoughtful , provocative , insistently humanizing film .", "positive", "surprise", "2015-05-01");
                        mydb.insertData("23	With a cast that includes some of the top actors working in independent film , Lovely & Amazing involves us because it is so incisive , so bleakly amusing about how we go about our lives .", "positive", "happy", "2015-05-01");
                        mydb.insertData("24	A disturbing and frighteningly evocative assembly of imagery and hypnotic music composed by Philip Glass .", "positive", "fear", "2015-05-01");
                        mydb.insertData("25	Not for everyone , but for those with whom it will connect , it 's a nice departure from standard moviegoing fare .", "negative", "happy", "2015-05-01");
                        mydb.insertData("26	Scores a few points for doing what it does with a dedicated and good-hearted professionalism .", "positive", "happy", "2015-05-01");
                        mydb.insertData("27	Occasionally melodramatic , it 's also extremely effective .", "negative", "happy", "2015-05-01");
                        mydb.insertData("28	An idealistic love story that brings out the latent 15-year-old romantic in everyone .", "positive", "happy", "2015-05-01");
                        mydb.insertData("29	At about 95 minutes , Treasure Planet maintains a brisk pace as it races through the familiar story .", "positive", "happy", "2015-05-01");
                        mydb.insertData("30	However , it lacks grandeur and that epic quality often associated with Stevenson 's tale as well as with earlier Disney efforts .", "negative", "happy", "2015-05-01");
                        mydb.insertData("31	It helps that Lil Bow Wow ... tones down his pint-sized gangsta act to play someone who resembles a real kid .", "negative", "surprise", "2015-05-01");
                        mydb.insertData("32	Guaranteed to move anyone who ever shook , rattled , or rolled .", "positive", "anger", "2015-05-01");
                        mydb.insertData("33	A masterful film from a master filmmaker , unique in its deceptive grimness , compelling in its fatalist worldview .", "positive", "sad", "2015-05-01");
                        mydb.insertData("34	Light , cute and forgettable .", "negative", "sad", "2015-05-01");
                        mydb.insertData("35	If there 's a way to effectively teach kids about the dangers of drugs , I think it 's in projects like the -LRB- unfortunately R-rated -RRB- Paid .", "negative", "sad", "2015-05-01");
                        mydb.insertData("36	While it would be easy to give Crush the new title of Two Weddings and a Funeral , it 's a far more thoughtful film than any slice of Hugh Grant whimsy .", "positive", "anger", "2015-05-01");
                        mydb.insertData("37	Though everything might be literate and smart , it never took off and always seemed static .", "negative", "sad", "2015-05-01");
                        mydb.insertData("40	Though it is by no means his best work , Laissez-Passer is a distinguished and distinctive effort by a bona-fide master , a fascinating film replete with rewards to be had by all willing to make the effort to reap them .", "positive", "happy", "2015-05-01");
                        mydb.insertData("41	Like most Bond outings in recent years , some of the stunts are so outlandish that they border on being cartoonlike .", "negative", "happy", "2015-05-01");
                        mydb.insertData("42	A heavy reliance on CGI technology is beginning to creep into the series .", "negative", "disgust", "2015-05-01");
                        mydb.insertData("43	Newton draws our attention like a magnet , and acts circles around her better known co-star , Mark Wahlberg .", "positive", "happy", "2015-05-01");
                        mydb.insertData("44	The story loses its bite in a last-minute happy ending that 's even less plausible than the rest of the picture .", "negative", "happy", "2015-05-01");
                        mydb.insertData("45	Much of the way , though , this is a refreshingly novel ride .", "positive", "happy", "2015-05-01");
                        mydb.insertData("46	Fuller would surely have called this gutsy and at times exhilarating movie a great yarn .", "positive", "surprise", "2015-05-01");
                        mydb.insertData("47	The film makes a strong case for the importance of the musicians in creating the Motown sound .", "positive", "happy", "2015-05-01");
                        mydb.insertData("48	Karmen moves like rhythm itself , her lips chanting to the beat , her long , braided hair doing little to wipe away the jeweled beads of sweat .", "negative", "happy", "2015-05-01");
                        mydb.insertData("49	Gosling provides an amazing performance that dwarfs everything else in the film .", "positive", "happy", "2015-05-01");
                        mydb.insertData("50	A real movie , about real people , that gives us a rare glimpse into a culture most of us do n't know .", "positive", "sad", "2015-05-01");
                        mydb.insertData("51	Tender yet lacerating and darkly funny fable .", "positive", "happy", "2015-05-01");
                        mydb.insertData("52	May be spoofing an easy target -- those old ' 50 's giant creature features -- but ... it acknowledges and celebrates their cheesiness as the reason why people get a kick out of watching them today .", "negative", "sad", "2015-05-01");
                        mydb.insertData("53	An engaging overview of Johnson 's eccentric career .", "positive", "happy", "2015-05-01");
                        mydb.insertData("54	In its ragged , cheap and unassuming way , the movie works .", "negative", "anger", "2015-05-01");
                        mydb.insertData("55	Some actors have so much charisma that you 'd be happy to listen to them reading the phone book .", "positive", "happy", "2015-05-01");
                        mydb.insertData("56	Hugh Grant and Sandra Bullock are two such likeable actors .", "positive", "happy", "2015-05-01");
                        mydb.insertData("57	Sandra Nettelbeck beautifully orchestrates the transformation of the chilly , neurotic , and self-absorbed Martha as her heart begins to open .", "positive", "happy", "2015-05-01");
                        mydb.insertData("58	Behind the snow games and lovable Siberian huskies -LRB- plus one sheep dog -RRB- , the picture hosts a parka-wrapped dose of heart .", "negative", "happy", "2015-05-01");
                        mydb.insertData("59	Everytime you think Undercover Brother has run out of steam , it finds a new way to surprise and amuse .", "positive", "surprise", "2015-05-01");
                        mydb.insertData("60	Manages to be original , even though it rips off many of its ideas .", "negative", "sad", "2015-05-01");
                        mydb.insertData("62	You 'd think by now America would have had enough of plucky British eccentrics with hearts of gold .", "negative", "fear", "2015-05-01");
                        mydb.insertData("63	Yet the act is still charming here .", "positive", "happy", "2015-05-01");
                        mydb.insertData("64	Whether or not you 're enlightened by any of Derrida 's lectures on `` the other '' and `` the self , '' Derrida is an undeniably fascinating and playful fellow .", "positive", "sad", "2015-05-01");
                        mydb.insertData("65	A pleasant enough movie , held together by skilled ensemble actors .", "positive", "happy", "2015-05-01");
                        mydb.insertData("66	This is the best American movie about troubled teens since 1998 's Whatever .", "positive", "fear", "2015-05-01");
                        mydb.insertData("67	Disney has always been hit-or-miss when bringing beloved kids ' books to the screen ... Tuck Everlasting is a little of both .", "positive", "happy", "2015-05-01");
                        mydb.insertData("68	Just the labour involved in creating the layered richness of the imagery in this chiaroscuro of madness and light is astonishing .", "positive", "happy", "2015-05-01");
                        mydb.insertData("69	The animated subplot keenly depicts the inner struggles of our adolescent heroes - insecure , uncontrolled , and intense .", "negative", "anger", "2015-05-01");
                        mydb.insertData("72	Part of the charm of Satin Rouge is that it avoids the obvious with humour and lightness .", "positive", "sad", "2015-05-01");
                        mydb.insertData("73	Son of the Bride may be a good half-hour too long but comes replete with a flattering sense of mystery and quietness .", "negative", "happy", "2015-05-01");
                        mydb.insertData("74	A simmering psychological drama in which the bursts of sudden violence are all the more startling for the slow buildup that has preceded them .", "neutral", "anger", "2015-05-01");
                        mydb.insertData("75	A taut , intelligent psychological drama .", "positive", "happy", "2015-05-01");
                        mydb.insertData("76	A truly moving experience , and a perfect example of how art -- when done right -- can help heal , clarify , and comfort .", "positive", "sad", "2015-05-01");
                        mydb.insertData("77	This delicately observed story , deeply felt and masterfully stylized , is a triumph for its maverick director .", "positive", "happy", "2015-05-01");
                        mydb.insertData("78	At heart the movie is a deftly wrought suspense yarn whose richer shadings work as coloring rather than substance .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("79	The appearance of Treebeard and Gollum 's expanded role will either have you loving what you 're seeing , or rolling your eyes .", "negative", "happy", "2015-05-01");
                        mydb.insertData("80	I loved it !", "positive", "happy", "2015-05-01");
                        mydb.insertData("81	Gollum 's ` performance ' is incredible !", "positive", "surprise", "2015-05-01");
                        mydb.insertData("84	Haneke challenges us to confront the reality of sexual aberration .", "positive", "happy", "2015-05-01");
                        mydb.insertData("85	Absorbing and disturbing -- perhaps more disturbing than originally intended -- but a little clarity would have gone a long way .", "negative", "sad", "2015-05-01");
                        mydb.insertData("86	It 's the best film of the year so far , the benchmark against which all other Best Picture contenders should be measured .", "positive", "happy", "2015-05-01");
                        mydb.insertData("87	Painful to watch , but viewers willing to take a chance will be rewarded with two of the year 's most accomplished and riveting film performances .", "positive", "happy", "2015-05-01");
                        mydb.insertData("88	This is a startling film that gives you a fascinating , albeit depressing view of Iranian rural life close to the Iraqi border .", "positive", "sad", "2015-05-01");
                        mydb.insertData("89	A few artsy flourishes aside , Narc is as gritty as a movie gets these days .", "negative", "happy", "2015-05-01");
                        mydb.insertData("90	While The Isle is both preposterous and thoroughly misogynistic , its vistas are incredibly beautiful to look at .", "positive", "happy", "2015-05-01");
                        mydb.insertData("91	Together , Tok and O orchestrate a buoyant , darkly funny dance of death .", "positive", "happy", "2015-05-01");
                        mydb.insertData("92	In the process , they demonstrate that there 's still a lot of life in Hong Kong cinema .", "positive", "sad", "2015-05-01");
                        mydb.insertData("93	Director Kapur is a filmmaker with a real flair for epic landscapes and adventure , and this is a better film than his earlier English-language movie , the overpraised Elizabeth .", "positive", "happy", "2015-05-01");
                        mydb.insertData("94	The movie is a blast of educational energy , as bouncy animation and catchy songs escort you through the entire 85 minutes .", "positive", "happy", "2015-05-01");
                        mydb.insertData("95	A sports movie with action that 's exciting on the field and a story you care about off it .", "positive", "surprise", "2015-05-01");
                        mydb.insertData("96	Doug Liman , the director of Bourne , directs the traffic well , gets a nice wintry look from his locations , absorbs us with the movie 's spycraft and uses Damon 's ability to be focused and sincere .", "positive", "happy", "2015-05-01");
                        mydb.insertData("97	The tenderness of the piece is still intact .", "positive", "sad", "2015-05-01");
                        mydb.insertData("100	While McFarlane 's animation lifts the film firmly above the level of other coming-of-age films ... it 's also so jarring that it 's hard to get back into the boys ' story .", "negative", "happy", "2015-05-01");
                        mydb.insertData("101	If nothing else , this movie introduces a promising , unusual kind of psychological horror .", "positive", "fear", "2015-05-01");
                        mydb.insertData("102	In a normal screen process , these bromides would be barely enough to sustain an interstitial program on the Discovery Channel .", "negative", "sad", "2015-05-01");
                        mydb.insertData("103	But in Imax 3-D , the clichÃ©s disappear into the vertiginous perspectives opened up by the photography .", "negative", "sad", "2015-05-01");
                        mydb.insertData("104	Writer-director Burger imaginatively fans the embers of a dormant national grief and curiosity that has calcified into chronic cynicism and fear .", "negative", "fear", "2015-05-01");
                        mydb.insertData("107	Chicago is sophisticated , brash , sardonic , completely joyful in its execution .", "negative", "happy", "2015-05-01");
                        mydb.insertData("108	Steve Irwin 's method is Ernest Hemmingway at accelerated speed and volume .", "negative", "happy", "2015-05-01");
                        mydb.insertData("109	A refreshing Korean film about five female high school friends who face an uphill battle when they try to take their relationships into deeper waters .", "positive", "fear", "2015-05-01");
                        mydb.insertData("110	On the surface , it 's a lovers-on-the-run crime flick , but it has a lot in common with Piesiewicz 's and Kieslowski 's earlier work , films like The Double Life of Veronique .", "positive", "anger", "2015-05-01");
                        mydb.insertData("111	The values that have held the Enterprise crew together through previous adventures and perils do so again-courage , self-sacrifice and patience under pressure .", "negative", "anger", "2015-05-01");
                        mydb.insertData("112	If it 's possible for a sequel to outshine the original , then SL2 does just that .", "negative", "happy", "2015-05-01");
                        mydb.insertData("113	A romantic comedy that operates by the rules of its own self-contained universe .", "positive", "happy", "2015-05-01");
                        mydb.insertData("114	4 friends , 2 couples , 2000 miles , and all the Pabst Blue Ribbon beer they can drink - it 's the ultimate redneck road-trip .", "negative", "happy", "2015-05-01");
                        mydb.insertData("117	A feel-good picture in the best sense of the term .", "positive", "happy", "2015-05-01");
                        mydb.insertData("118	Edited and shot with a syncopated style mimicking the work of his subjects , Pray turns the idea of the documentary on its head , making it rousing , invigorating fun lacking any MTV puffery .", "positive", "happy", "2015-05-01");
                        mydb.insertData("119	A mostly intelligent , engrossing and psychologically resonant suspenser .", "positive", "happy", "2015-05-01");
                        mydb.insertData("120	It 's this memory-as-identity obviation that gives Secret Life its intermittent unease , reaffirming that long-held illusions are indeed reality , and that erasing them recasts the self .", "negative", "sad", "2015-05-01");
                        mydb.insertData("121	Hip-hop has a history , and it 's a metaphor for this love story .", "positive", "happy", "2015-05-01");
                        mydb.insertData("122	In scope , ambition and accomplishment , Children of the Century ... takes Kurys ' career to a whole new level .", "positive", "happy", "2015-05-01");
                        mydb.insertData("123	This may not have the dramatic gut-wrenching impact of other Holocaust films , but it 's a compelling story , mainly because of the way it 's told by the people who were there .", "positive", "surprise", "2015-05-01");
                        mydb.insertData("124	Between the drama of Cube ?", "neutral", "sad", "2015-05-01");
                        mydb.insertData("125	s personal revelations regarding what the shop means in the big picture , iconic characters gambol fluidly through the story , with charming results .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("126	A gentle , compassionate drama about grief and healing .", "positive", "sad", "2015-05-01");
                        mydb.insertData("127	Somewhere short of Tremors on the modern B-scene : neither as funny nor as clever , though an agreeably unpretentious way to spend ninety minutes .", "negative", "happy", "2015-05-01");
                        mydb.insertData("128	Digital-video documentary about stand-up comedians is a great glimpse into a very different world .", "positive", "happy", "2015-05-01");
                        mydb.insertData("129	Unlike most teen flicks , Swimming takes its time to tell its story , casts mostly little-known performers in key roles , and introduces some intriguing ambiguity .", "positive", "happy", "2015-05-01");
                        mydb.insertData("130	An enthralling , playful film that constantly frustrates our desire to know the ` truth ' about this man , while deconstructing the very format of the biography in a manner that Derrida would doubtless give his blessing to .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("131	`` Extreme Ops '' exceeds expectations .", "positive", "anger", "2015-05-01");
                        mydb.insertData("132	Good fun , good action , good acting , good dialogue , good pace , good cinematography .", "positive", "happy", "2015-05-01");
                        mydb.insertData("133	You Should Pay Nine Bucks for This : Because you can hear about suffering Afghan refugees on the news and still be unaffected .", "negative", "sad", "2015-05-01");
                        mydb.insertData("134	Dramas like this make it human .", "positive", "anger", "2015-05-01");
                        mydb.insertData("135	A thunderous ride at first , quiet cadences of pure finesse are few and far between ; their shortage dilutes the potency of otherwise respectable action .", "positive", "happy", "2015-05-01");
                        mydb.insertData("136	Still , this flick is fun , and host to some truly excellent sequences .", "positive", "happy", "2015-05-01");
                        mydb.insertData("137	It 's obviously struck a responsive chord with many South Koreans , and should work its magic in other parts of the world .", "positive", "happy", "2015-05-01");
                        mydb.insertData("138	Run , do n't walk , to see this barbed and bracing comedy on the big screen .", "negative", "anger", "2015-05-01");
                        mydb.insertData("139	A classy item by a legend who may have nothing left to prove but still has the chops and drive to show how its done .", "positive", "sad", "2015-05-01");
                        mydb.insertData("140	It is nature against progress .", "negative", "sad", "2015-05-01");
                        mydb.insertData("141	In Fessenden 's horror trilogy , this theme has proved important to him and is especially so in the finale .", "positive", "fear", "2015-05-01");
                        mydb.insertData("142	It 's not exactly a gourmet meal but the fare is fair , even coming from the drive-thru .", "negative", "sad", "2015-05-01");
                        mydb.insertData("143	This is what IMAX was made for : Strap on a pair of 3-D goggles , shut out the real world , and take a vicarious voyage to the last frontier -- space .", "negative", "happy", "2015-05-01");
                        mydb.insertData("144	Merely as a technical , logistical feat , Russian Ark marks a cinematic milestone .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("145	-LRB- Schweiger is -RRB- talented and terribly charismatic , qualities essential to both movie stars and social anarchists .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("146	It 's a great deal of sizzle and very little steak .", "positive", "happy", "2015-05-01");
                        mydb.insertData("147	But what spectacular sizzle it is !", "positive", "disgust", "2015-05-01");
                        mydb.insertData("148	... In this incarnation its fizz is infectious .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("149	An original gem about an obsession with time .", "positive", "sad", "2015-05-01");
                        mydb.insertData("150	It will delight newcomers to the story and those who know it from bygone days .", "positive", "happy", "2015-05-01");
                        mydb.insertData("151	Gloriously goofy -LRB- and gory -RRB- midnight movie stuff .", "negative", "anger", "2015-05-01");
                        mydb.insertData("152	The film overcomes the regular minefield of coming-of-age cliches with potent doses of honesty and sensitivity .", "positive", "happy", "2015-05-01");
                        mydb.insertData("153	If your senses have n't been dulled by slasher films and gorefests , if you 're a connoisseur of psychological horror , this is your ticket .", "negative", "fear", "2015-05-01");
                        mydb.insertData("154	It 's a minor comedy that tries to balance sweetness with coarseness , while it paints a sad picture of the singles scene .", "negative", "sad", "2015-05-01");
                        mydb.insertData("155	It is intensely personal and yet -- unlike Quills -- deftly shows us the temper of the times .", "positive", "happy", "2015-05-01");
                        mydb.insertData("156	As lo-fi as the special effects are , the folks who cobbled Nemesis together indulge the force of humanity over hardware in a way that George Lucas has long forgotten .", "negative", "anger", "2015-05-01");
                        mydb.insertData("157	Like Mike does n't win any points for originality .", "negative", "happy", "2015-05-01");
                        mydb.insertData("158	It does succeed by following a feel-good formula with a winning style , and by offering its target audience of urban kids some welcome role models and optimism .", "positive", "happy", "2015-05-01");
                        mydb.insertData("159	It 's a hoot and a half , and a great way for the American people to see what a candidate is like when he 's not giving the same 15-cent stump speech .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("160	Far from perfect , but its heart is in the right place ... innocent and well-meaning .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("161	A sad , superior human comedy played out on the back roads of life .", "positive", "sad", "2015-05-01");
                        mydb.insertData("162	Waydowntown is by no means a perfect film , but its boasts a huge charm factor and smacks of originality .", "positive", "happy", "2015-05-01");
                        mydb.insertData("163	Tim Allen is great in his role but never hogs the scenes from his fellow cast , as there are plenty of laughs and good lines for everyone in this comedy .", "positive", "happy", "2015-05-01");
                        mydb.insertData("164	More a load of enjoyable , Conan-esque claptrap than the punishing , special-effects soul assaults the Mummy pictures represent .", "positive", "happy", "2015-05-01");
                        mydb.insertData("165	Enormously likable , partly because it is aware of its own grasp of the absurd .", "positive", "anger", "2015-05-01");
                        mydb.insertData("166	Here 's a British flick gleefully unconcerned with plausibility , yet just as determined to entertain you .", "positive", "happy", "2015-05-01");
                        mydb.insertData("167	It 's an old story , but a lively script , sharp acting and partially animated interludes make Just a Kiss seem minty fresh .", "positive", "happy", "2015-05-01");
                        mydb.insertData("168	Must be seen to be believed .", "neutral", "sad", "2015-05-01");
                        mydb.insertData("169	Ray Liotta and Jason Patric do some of their best work in their underwritten roles , but do n't be fooled : Nobody deserves any prizes here .", "negative", "happy", "2015-05-01");
                        mydb.insertData("170	Everything that has to do with Yvan and Charlotte , and everything that has to do with Yvan 's rambunctious , Jewish sister and her non-Jew husband , feels funny and true .", "positive", "happy", "2015-05-01");
                        mydb.insertData("171	The year 's happiest surprise , a movie that deals with a real subject in an always surprising way .", "positive", "surprise", "2015-05-01");
                        mydb.insertData("172	Fans of Behan 's work and of Irish movies in general will be rewarded by Borstal Boy .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("173	Its mysteries are transparently obvious , and it 's too slowly paced to be a thriller .", "negative", "sad", "2015-05-01");
                        mydb.insertData("174	-LRB- But it 's -RRB- worth recommending because of two marvelous performances by Michael Caine and Brendan Fraser .", "positive", "happy", "2015-05-01");
                        mydb.insertData("175	The film is faithful to what one presumes are the book 's twin premises -- that we become who we are on the backs of our parents , but we have no idea who they were at our age ; and that time is a fleeting and precious commodity no matter how old you are .", "negative", "sad", "2015-05-01");
                        mydb.insertData("176	Stephen Earnhart 's homespun documentary Mule Skinner Blues has nothing but love for its posse of trailer park denizens .", "negative", "happy", "2015-05-01");
                        mydb.insertData("177	A solidly seaworthy chiller .", "positive", "happy", "2015-05-01");
                        mydb.insertData("178	If you can get past the fantastical aspects and harsh realities of `` The Isle '' you 'll get a sock-you-in-the-eye flick that is a visual tour-de-force and a story that is unlike any you will likely see anywhere else .", "positive", "happy", "2015-05-01");
                        mydb.insertData("179	There are as many misses as hits , but ultimately , it finds humor in the foibles of human behavior , and it 's a welcome return to the roots of a genre that should depend on surprises .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("180	A well-made thriller with a certain level of intelligence and non-reactionary morality .", "positive", "happy", "2015-05-01");
                        mydb.insertData("181	There 's enough science to make it count as educational , and enough beauty to make it unforgettable .", "positive", "disgust", "2015-05-01");
                        mydb.insertData("182	Remains a solid , if somewhat heavy-handed , account of the near-disaster ... done up by Howard with a steady , if not very imaginative , hand .", "negative", "happy", "2015-05-01");
                        mydb.insertData("183	Makmalbaf follows a resolutely realistic path in this uncompromising insight into the harsh existence of the Kurdish refugees of Iran 's borderlands .", "negative", "happy", "2015-05-01");
                        mydb.insertData("184	For a good chunk of its running time , Trapped is an effective and claustrophobic thriller .", "negative", "happy", "2015-05-01");
                        mydb.insertData("185	Most of Crush is a clever and captivating romantic comedy with a welcome pinch of tartness .", "positive", "anger", "2015-05-01");
                        mydb.insertData("188	Rare is the ` urban comedy ' that even attempts the insight and honesty of this disarming indie .", "positive", "happy", "2015-05-01");
                        mydb.insertData("189	Ranks among Willams ' best screen work .", "positive", "happy", "2015-05-01");
                        mydb.insertData("190	Engagingly captures the maddening and magnetic ebb and flow of friendship .", "positive", "happy", "2015-05-01");
                        mydb.insertData("191	An experience so engrossing it is like being buried in a new environment .", "positive", "sad", "2015-05-01");
                        mydb.insertData("192	It 's traditional moviemaking all the way , but it 's done with a lot of careful period attention as well as some very welcome wit .", "positive", "happy", "2015-05-01");
                        mydb.insertData("193	Maybe it 's just because this past year has seen the release of some of the worst film comedies in decades ... But honestly , Analyze That really is n't all that bad .", "negative", "sad", "2015-05-01");
                        mydb.insertData("194	A droll , well-acted , character-driven comedy with unexpected deposits of feeling .", "positive", "surprise", "2015-05-01");
                        mydb.insertData("195	This is simply the most fun you 'll ever have with a documentary !", "positive", "happy", "2015-05-01");
                        mydb.insertData("196	A very funny movie .", "positive", "happy", "2015-05-01");
                        mydb.insertData("197	Watching Haneke 's film is , aptly enough , a challenge and a punishment .", "positive", "happy", "2015-05-01");
                        mydb.insertData("198	But watching Huppert , a great actress tearing into a landmark role , is riveting .", "positive", "happy", "2015-05-01");
                        mydb.insertData("199	A cop story that understands the medium amazingly well .", "positive", "happy", "2015-05-01");
                        Toast.makeText(HomePage.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            NavigationDrawer drawerFragment = (NavigationDrawer)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        }


        return super.onOptionsItemSelected(item);
    }

}