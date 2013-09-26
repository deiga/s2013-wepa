package wad.datatables.init;

import java.util.Random;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wad.datatables.domain.Book;
import wad.datatables.repository.BookRepository;

@Component
public class InitService {

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    @Transactional(readOnly = false)
    private void init() {
        Scanner sc = new Scanner(books);
        Random random = new Random();
        
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.isEmpty()) {
                continue;
            }
            
            String[] data = line.split(" by ");
            if(data.length != 2) {
                continue;
            }
            
            
            String title = data[0];
            
            Book book = new Book();
            book.setTitle(title);
            book.setPages(Integer.valueOf(random.nextInt(2000)).longValue());
            
            bookRepository.save(book);
        }
    }
    
    private final String books = "I Win! by U. Lose\n"
            + "Robots by Anne Droid\n"
            + "Danger! by Luke Out\n"
            + "Cloning by Irma Dubble II\n"
            + "Hot Dog! by Frank Furter\n"
            + "Cry Wolf by Al Armist\n"
            + "Wake Up! by Sal Ammoniac\n"
            + "I'm Fine by Howard Yu\n"
            + "Gambling by Monty Carlos\n"
            + "Mensa Man by Gene Yuss\n"
            + "Big Fart! by Hugh Jass\n"
            + "Hypnotism by N. Tranced\n"
            + "Downpour! by Wayne Dwops\n"
            + "Full Moon by Seymour Buns\n"
            + "Sea Birds by Al Batross\n"
            + "Teach Me! by I. Wanda Know\n"
            + "I Say So! by Frank O. Pinion\n"
            + "Tug of War by Paul Hard\n"
            + "Surprised! by Omar Gosh\n"
            + "Beekeeping by A. P. Arry\n"
            + "Good Works by Ben Evolent\n"
            + "Golly Gosh! by G. Whiz\n"
            + "It's Magic! by Sven Gali\n"
            + "April Fool! by Sue Prize\n"
            + "Come on in! by Doris Open\n"
            + "Parachuting by Hugo First\n"
            + "Get Moving! by Sheik Aleg\n"
            + "I Like Fish by Ann Chovie\n"
            + "Leo Tolstoy by Warren Peace\n"
            + "May Flowers by April Showers\n"
            + "Pain Relief by Ann L. Gesick\n"
            + "It's Unfair! by Y. Me\n"
            + "Armed Heists by Robin Banks\n"
            + "How to Annoy by Aunt Agonize\n"
            + "Racketeering by Dennis Court\n"
            + "I Love Wills by Benny Fishery\n"
            + "Stop Arguing by Xavier Breath\n"
            + "Sofa so Good by Chester Field\n"
            + "Riel Ambush! by May T. Surprise\n"
            + "Falling Trees by Tim Burr\n"
            + "Monkey Shines by Bob Boone\n"
            + "Why Cars Stop by M.T. Tank\n"
            + "Turtle Racing by Eubie Quick\n"
            + "Military Rule by Marshall Law\n"
            + "I Like Liquor by Ethyl Alcohol\n"
            + "I Love Crowds by Morris Merrier\n"
            + "The Yellow River, by I. P. Freely\n"
            + "Off To Market by Tobias A. Pigg\n"
            + "A Great Plenty by E. Nuff\n"
            + "Mosquito Bites by Ivan Itch\n"
            + "My Lost Causes by Noah Veil\n"
            + "Grave Mistakes by Paul Bearer\n"
            + "Get Out There! by Sally Forth\n"
            + "Red Vegetables by B. Troot\n"
            + "Irish Flooring by Lynn O'Leum\n"
            + "Highway Travel by Dusty Rhodes\n"
            + "It's a Shocker by Alec Tricity\n"
            + "Keep it Clean! by Armand Hammer\n"
            + "I Hit the Wall by Isadore There\n"
            + "Ship Mysteries by Marie Celeste\n"
            + "I Hate the Sun by Gladys Knight\n"
            + "It's a Holdup! by Nick R. Elastic\n"
            + "He Disappeared! by Otto Sight\n"
            + "I Hate Fighting by Boris Hell\n"
            + "Mexican Revenge by Monty Zuma\n"
            + "I Didn't Do It! by Ivan Alibi\n"
            + "Life in Chicago by Wendy City\n"
            + "Without Warning by Oliver Sudden\n"
            + "Pain in My Body by Otis Leghurts\n"
            + "Desert Crossing by I. Rhoda Camel\n"
            + "Candle-Vaulting by Jack B. Nimble\n"
            + "Happy New Year! by Mary Christmas\n"
            + "You're Kidding! by Shirley U. Jest\n"
            + "Webster's Words by Dick Shunnary\n"
            + "Those Funny Dogs by Joe Kur\n"
            + "Wind Instruments by Tom Bone\n"
            + "Winning the Race by Vic Tree\n"
            + "Crocodile Dundee by Ali Gator\n"
            + "Covered Walkways by R. Kade\n"
            + "I Need Insurance by Justin Case\n"
            + "Whatchamacallit! by Thingum Bob\n"
            + "Let's Do it Now! by Igor Beaver\n"
            + "I'm Someone Else by Ima Nonymous\n"
            + "Animal Illnesses by Ann Thrax\n"
            + "He's Contagious! by Lucas Measles\n"
            + "The Great Escape by Freida Convict\n"
            + "Music of the Sea by Lawrence Whelk\n"
            + "Breaking the Law by Kermit A. Krime\n"
            + "Cooking Spaghetti by Al Dente\n"
            + "Smart Beer Making by Bud Wiser\n"
            + "Good Housekeeping by Lottie Dust\n"
            + "Mountain Climbing by Andover Hand\n"
            + "Theft and Robbery by Andy Tover\n"
            + "Equine Leg Cramps by Charlie Horse\n"
            + "The Lion Attacked by Claudia Armoff\n"
            + "Poetry in Baseball by Homer\n"
            + "I Love Mathematics by Adam Up\n"
            + "Exercise on Wheels by Cy Kling\n"
            + "Measles Collision! by Kay Rash\n"
            + "Unsolved Mysteries by N. Igma\n"
            + "I Lived in Detroit by Helen Earth\n"
            + "Lots of Excitement by Hugh N. Cry\n"
            + "String Instruments by Viola Player\n"
            + "Outdoor Activities by Alf Resco\n"
            + "Maritime Disasters by Andrea Doria\n"
            + "Smash His Lobster! by Buster Crabbe\n"
            + "The Unknown Rodent by A. Nonny Mouse\n"
            + "In the Arctic Ocean by Isa Berg\n"
            + "Perverted Mushrooms by M. Morel\n"
            + "Modern Tree Watches by Anna Log\n"
            + "Noise is Forbidden! by Nada Loud\n"
            + "I Must Fix the Car! by Otto Doit\n"
            + "Snakes of the World by Anna Conda\n"
            + "The Housing Problem by Rufus Quick\n"
            + "Artificial Clothing by Polly Ester\n"
            + "More for Your Money by Max Amize\n"
            + "If I Invited Him... by Woody Kum\n"
            + "Two Thousand Pounds! by Juan Ton\n"
            + "Assault with Battery by Eva Ready\n"
            + "Gunslingers with Gas by Wyatt Urp\n"
            + "Soak Your Ex-Husband by Ali Money\n"
            + "And the Other People by Allan Sundry\n"
            + "Overweight Vegetables by O. Beets\n"
            + "A Trip to the Dentist by Yin Pain\n"
            + "Mineralogy for Giants by Chris Tall\n"
            + "Bring to the Grocer's by R. List\n"
            + "Almost Missed the Bus by Justin Time\n"
            + "My Life in the Gutter by Yves Trough\n"
            + "Things to Cook Meat In by Stu Potts\n"
            + "Los Angeles Pachyderms by L.A. Funt\n"
            + "Clothes for Germ Kings by Mike Robes\n"
            + "Tyrant of the Potatoes by Dick Tater\n"
            + "I Hate Monday Mornings by Gaetan Oop\n"
            + "The Fall of a Watermelon by S. Platt\n"
            + "Pull with All You've Got! by Eve Ho\n"
            + "I Love Fractions by Lois C. Denominator\n"
            + "Military Defeats by Major Disaster and General Mayhem\n"
            + "Judging Fast Food by Warren Berger\n"
            + "I Lost My Balance by Eileen Dover and Paul Down\n"
            + "House Construction by Bill Jerome Holme\n"
            + "Kangaroo Illnesses by Marcus Wallaby, M.D.\n"
            + "Exotic Irish Plants by Phil O'Dendron\n"
            + "Musical Gunfighters by The Okay Chorale\n"
            + "A Whole Lot of Cats by Kitt N. Caboodle\n"
            + "I Work with Diamonds by Jules Sparkle\n"
            + "Lawyers of Suffering by Grin and Barrett\n"
            + "Flogging in the Army by Corporal Punishment\n"
            + "Errors and Accidents by Miss Takes and Miss Haps\n"
            + "Christmas for Baldies by Yule Brynner\n"
            + "Where to Find Islands by Archie Pelago\n"
            + "French Overpopulation by Francis Crowded\n"
            + "How to Tour the Prison by Robin Steele\n"
            + "I Like Weeding Gardens by Manuel Labour\n"
            + "Who Killed Cock Robin? by Howard I. Know\n"
            + "The Effects of Alcohol by Sir Osis of Liver\n"
            + "Daddy are We There Yet? by Miles Away\n"
            + "The Excitement of Trees by I. M. Board\n"
            + "No More Circuit Breakers! by Ira Fuse\n"
            + "You're a Bundle of Laughs by Vera Funny\n"
            + "The Industrial Revolution by Otto Mattick\n"
            + "Artificial Weightlessness by Andy Gravity\n"
            + "The Palace Roof has a Hole by Lee King\n"
            + "Ecclesiastical Infractions by Cardinal Sin\n"
            + "Preaching to Hell's Angels by Pastor Redlight\n"
            + "Songs from 'South Pacific' by Sam and Janet Evening\n"
            + "I Was a Cloakroom Attendant by Mahatma Coate\n"
            + "Fifty Yards to the Outhouse by Willy Makit; Foreward by Betty Wont\n"
            + "Foot Problems of Big Lumberjacks by Paul Bunion";
}
