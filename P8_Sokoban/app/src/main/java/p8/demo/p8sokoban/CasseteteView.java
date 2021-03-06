package p8.demo.p8sokoban;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.content.ContentValues.TAG;

public class CasseteteView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	// Declaration des images
    private Bitmap 		blue1;
    private Bitmap 		green1;
    private Bitmap 		black1;
    private Bitmap 		fond1;
    private Bitmap      marron1;
    private Bitmap      red1;
    private Bitmap 		violet1;
    private Bitmap      grena1;
    private Bitmap 		diamant;
    private Bitmap 		perso;
    private Bitmap 		espace;
    private Bitmap[] []	espace1 = new Bitmap[5][5];
    private Bitmap 		up;
    private Bitmap 		down;
    private Bitmap 		left;
    private Bitmap 		right;
    private Bitmap 		win;


    // Declaration des objets Ressources et Context permettant d'acc�der aux ressources de notre application et de les charger
    private Resources 	mRes;    
    private Context 	mContext;

    // tableau modelisant la carte du jeu
    int[][] carte;
    
    // ancres pour pouvoir centrer la carte du jeu
    int        carteTopAnchor;                   // coordonn�es en Y du point d'ancrage de notre carte
    int        carteLeftAnchor;                  // coordonn�es en X du point d'ancrage de notre carte

    // taille de la carte
    final int    carteWidth    = 9;//getWidth();
    final int    carteHeight   = 5;//getHeight();
    static final int    carteTileSize = 20;




    // constante modelisant les differentes types de cases
    static final int    CST_black     = 0;
    static final int    CST_marron     = 1;
    static final int    CST_red     = 2;
    static final int    CST_green     = 3;
    static final int    CST_blue     = 4;
    static final int    CST_violet      = 5;
    static final int    CST_grena     = 6;
    static final int    CST_espace      = 8;
    static final int    CST_fond1      = 7;
    static final int    CST_diamant   = 9;
    static final int    CST_zone      = 11;
    static final int    CST_vide      = 10;
    int tmpx;
    int tmpy;
    //static final int    CST_fond      = 5;







    // tableau de reference du terrain
    int [][] ref    = {
        {CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace},
        {CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace},
        {CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace},
        {CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace},
        {CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace, CST_espace}
    };


    int [][] solution    = {
            {CST_grena, CST_grena, CST_green, CST_black, CST_black, CST_black, CST_black, CST_marron, CST_marron},
            {CST_grena, CST_grena, CST_green, CST_green, CST_green, CST_black, CST_marron, CST_marron, CST_marron},
            {CST_grena, CST_red, CST_red, CST_red, CST_green, CST_black, CST_marron, CST_violet, CST_marron},
            {CST_grena, CST_grena, CST_blue, CST_green, CST_green, CST_black, CST_violet, CST_violet, CST_violet},
            {CST_blue, CST_blue, CST_blue, CST_blue, CST_blue, CST_blue, CST_violet, CST_violet, CST_violet}
    };

    int xPlayer;
    int yPlayer;

    // position de reference des cubres rouges
    int [][] reftabred   = {
        {0, 0},
        {1, 0},
        {2, 0}
    };


    // position courante des diamants
    int [][] tabred   = {
            {0, 0},
            {1, 0},
            {2, 0}
    };

    //position de reference de la figure rouge
    int xred=0;
    int yred= 478-carteTileSize;

    // position de reference du joueur
    int refxred=0;
    int refyred=478-carteTileSize;

    int hred=1;
    int lred=3;

    int xfred;
    int yfred;

    boolean touch1=false;

    // position de reference des cubes vert
    int [][] reftabgreen   = {
            {0, 0},
            {0, 1},
            {1, 1},
            {1, 3},
            {2, 1},
            {2, 2},
            {2, 3}
    };


    // position courante des cubes vert
    int [][] tabgreen   = {
            /*
            {4, 1},
            {5, 1},
            {5, 2},
            {3, 3},
            {4, 3},
            {5, 3},
            {3, 4}*/
            {0, 0},
            {0, 1},
            {1, 1},
            {1, 3},
            {2, 1},
            {2, 2},
            {2, 3}

    };

    //position de reference de la figure verte
    int xgreen=3*carteTileSize;
    int ygreen= 478-4*carteTileSize;

    // position de reference du joueur
    int refxgreen=0;
    int refygreen=0;

    int lgreen=3;
    int hgreen=4;

    int xfgreen;
    int yfgreen;

    boolean touch2=false;


    // position de reference des cubes bleu
    int [][] reftabblue   = {
            {0, 0},
            {1, 0},
            {2, 0},
            {2, -1},
            {3, 0},
            {4, 0},
            {5, 0}
    };


    // position courante des cubes bleu
    int [][] tabblue   = {
            /*
            {7, 1},
            {8, 1},
            {9, 1},
            {9, 2},
            {10, 1},
            {11, 1},
            {12, 1}
            {0, 0},
            {-2, -1},
            {-1, -1},
            {0, -1},
            {1, -1},
            {2, -1},
            {3, -1}*/
            {0, 0},
            {1, 0},
            {2, 0},
            {2, -1},
            {3, 0},
            {4, 0},
            {5, 0}

    };

    //position de reference de la figure bleu
    int xblue=7*carteTileSize;
    int yblue= 478-1*carteTileSize;

    // position de reference du joueur
    int refxblue=0;
    int refyblue=0;

    int hblue=2;
    int lblue=6;

    int xfblue;
    int yfblue;

    boolean touch3=false;

    // position de reference des cubes marron
    int [][] reftabmarron   = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, -1},
            {2, 0},
            {2, -1},
            {2, 1}
    };


    // position courante des cubes marron
    int [][] tabmarron   = {
            /*{0, 3},
            {0, 4},
            {1, 4},
            {1, 5},
            {2, 3},
            {2, 4},
            {2, 5}

            {0, 0},
            {-1, -1},
            {-1, -2},
            {0, -1},
            {1, 0},
            {1, -1},
            {1, -2}*/
            {0, 0},
            {0, 1},
            {1, 0},
            {1, -1},
            {2, 0},
            {2, -1},
            {2, 1}
    };

    //position de reference de la figure marron
    int xmarron=0;
    int ymarron= 478-4*carteTileSize;

    // position de reference du joueur
    int refxmarron=0;
    int refymarron=0;

    int lmarron=3;
    int hmarron=3;

    int xfmarron;
    int yfmarron;

    boolean touch4=false;

    // position de reference des cubes grena
    int [][] reftabgrena   = {
            {0, 0},
            {0, 1},
            {0, 2},
            {0, 3},
            {1, 0},
            {1, 1},
            {1, 3}
    };


    // position courante des cubes grena
    int [][] tabgrena   = {
            /*{6, 2},
            {6, 3},
            {6, 4},
            {6, 5},
            {7, 2},
            {7, 4},
            {7, 5}

            {0, 0},
            {0, -1},
            {0, -2},
            {0, -3},
            {1, 0},
            {1, -1},
            {1, -3}*/
            {0, 0},
            {0, 1},
            {0, 2},
            {0, 3},
            {1, 0},
            {1, 1},
            {1, 3}
    };

    //position de reference de la figure grena
    int xgrena=6*carteTileSize;
    int ygrena= 478-5*carteTileSize;

    // position de reference du joueur
    int refxgrena=2;
    int refygrena=4;

    int hgrena=4;
    int lgrena=2;

    int xfgrena;
    int yfgrena;

    boolean touch5=false;

    // position de reference des cubes black
    int [][] reftabblack   = {
            {0, 0},
            {1, 0},
            {2, 0},
            {2, 1},
            {2, 2},
            {2, 3},
            {3, 0}
    };


    // position courante des cubes black
    int [][] tabblack   = {
            /*{8, 5},
            {9, 5},
            {10, 2},
            {10, 3},
            {10, 4},
            {10, 5},
            {11, 5}

            {0, 0},
            {1, 0},
            {2, 0},
            {2, -1},
            {2, -2},
            {2, -3},
            {3, 0}*/
            {0, 0},
            {1, 0},
            {2, 0},
            {2, 1},
            {2, 2},
            {2, 3},
            {3, 0}

    };

    //position de reference de la figure black
    int xblack=8*carteTileSize;
    int yblack= 478-5*carteTileSize;

    // position de reference du joueur
    int refxblack=0;
    int refyblack=0;

    int hblack=4;
    int lblack=4;


    int xfblack;
    int yfblack;

    boolean touch6=false;

    // position de reference des cubes violet
    int [][] reftabviolet   = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, -1},
            {1, 1},
            {2, 0},
            {2, 1}
    };


    // position courante des cubes violet
    int [][] tabviolet   = {
            /*{0, 0},
            {0, 1},
            {1, 0},
            {1, 1},
            {1, 2},
            {2, 0},
            {2, 1}

            {0, 0},
            {-1, -1},
            {-1, -2},
            {0, -1},
            {0, -2},
            {1, -1},
            {1, -2}*/
            {0, 0},
            {0, 1},
            {1, 0},
            {1, -1},
            {1, 1},
            {2, 0},
            {2, 1}

    };

    //position de reference de la figure violet
    int xviolet=13*carteTileSize;
    int yviolet=478-(2*carteTileSize);

    // position de reference du joueur
    int refxviolet=13*carteTileSize;
    int refyviolet=13*carteTileSize;

    int hviolet=3;
    int lviolet=3;

    int xfviolet;
    int yfviolet;

    boolean touch7=false;







    /* compteur et max pour animer les zones d'arriv�e des diamants */
        int currentStepZone = 0;
        int maxStepZone     = 4;  

        // thread utiliser pour animer les zones de depot des diamants
        private     boolean in      = true;
        private     Thread  cv_thread;        
        SurfaceHolder holder;
        
        Paint paint;
        Boolean filter;


    /**
     * The constructor called from the main JetBoy activity
     * 
     * @param context 
     * @param attrs 
     */


    public CasseteteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        
        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed        
    	holder = getHolder();
        holder.addCallback(this);    
        
        // chargement des images
        mContext	= context;
        mRes 		= mContext.getResources();
        violet1 	= BitmapFactory.decodeResource(mRes, R.drawable.violet);
        grena1		= BitmapFactory.decodeResource(mRes, R.drawable.grena);
        marron1 	= BitmapFactory.decodeResource(mRes, R.drawable.marron);
        red1		= BitmapFactory.decodeResource(mRes, R.drawable.red);
        green1 		= BitmapFactory.decodeResource(mRes, R.drawable.green);
        blue1 		= BitmapFactory.decodeResource(mRes, R.drawable.blue);
        black1 		= BitmapFactory.decodeResource(mRes, R.drawable.black);
        diamant		= BitmapFactory.decodeResource(mRes, R.drawable.diamant);
    	espace		= BitmapFactory.decodeResource(mRes, R.drawable.espace);
        espace1[0] [0]	= BitmapFactory.decodeResource(mRes, R.drawable.espace_1);
        espace1[1] [1]	= BitmapFactory.decodeResource(mRes, R.drawable.espace_2);
        espace1[0][2] 	= BitmapFactory.decodeResource(mRes, R.drawable.espace_3);
        espace1[0][3] 	= BitmapFactory.decodeResource(mRes, R.drawable.espace_4);
    	fond1 		= BitmapFactory.decodeResource(mRes, R.drawable.fond);
    	//up 			= BitmapFactory.decodeResource(mRes, R.drawable.up);
    	//down 		= BitmapFactory.decodeResource(mRes, R.drawable.down);
    	//left 		= BitmapFactory.decodeResource(mRes, R.drawable.left);
    	//right 		= BitmapFactory.decodeResource(mRes, R.drawable.right);
    	win 		= BitmapFactory.decodeResource(mRes, R.drawable.win);
        //fond      = BitmapFactory.decodeResource(mRes, R.drawable.fond);




    	// initialisation des parmametres du jeu
    	initparameters();

    	// creation du thread
        cv_thread   = new Thread(this);
        // prise de focus pour gestion des touches
        setFocusable(true);


        	
    	
    }


    //Bitmap btmp = Bitmap.create(drawable.getBitmap());





    // chargement du niveau a partir du tableau de reference du niveau
    private void loadlevel() {
    	for (int i=0; i< carteWidth; i++) {
            for (int j=0; j< carteHeight; j++) {
                carte[j][i]= ref[j][i];
            }
        }	
    }    
    
    // initialisation du jeu
    public void initparameters() {
    	paint = new Paint();
    	paint.setColor(0x000000);
    	
    	paint.setDither(true);
    	paint.setColor(0x000000);  //pour les bordure des cases
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setStrokeJoin(Paint.Join.ROUND);
    	paint.setStrokeCap(Paint.Cap.ROUND);
    	paint.setStrokeWidth(10);
    	paint.setTextAlign(Paint.Align.LEFT);
        carte           = new int[carteHeight][carteWidth];
        loadlevel();
        carteTopAnchor  = getHeight()/5;
        carteLeftAnchor = getWidth()/5;

       for (int i=0; i< 3; i++) {
            tabred[i][1] = reftabred[i][1];
            tabred[i][0] = reftabred[i][0];
        }



        for (int i=0; i< 7; i++) {
            tabgreen[i][1] = reftabgreen[i][1];
            tabgreen[i][0] = reftabgreen[i][0];
        }



        for (int i=0; i< 7; i++) {
            tabmarron[i][1] = reftabmarron[i][1];
            tabmarron[i][0] = reftabmarron[i][0];
        }

        for (int i=0; i< 7; i++) {
            tabblack[i][1] = reftabblack[i][1];
            tabblack[i][0] = reftabblack[i][0];
        }

        for (int i=0; i< 7; i++) {
            tabblue[i][1] = reftabblue[i][1];
            tabblue[i][0] = reftabblue[i][0];
        }

        for (int i=0; i< 7; i++) {
            tabgrena[i][1] = reftabgrena[i][1];
            tabgrena[i][0] = reftabgrena[i][0];
        }

        for (int i=0; i< 7; i++) {
            tabviolet[i][1] = reftabviolet[i][1];
            tabviolet[i][0] = reftabviolet[i][0];
        }


        if ((cv_thread!=null) && (!cv_thread.isAlive())) {        	
        	cv_thread.start();
        	Log.e("-FCT-", "cv_thread.start()");
        }
    }    



    // dessin du gagne si gagne
    private void paintwin(Canvas canvas) {
    	canvas.drawBitmap(win, carteLeftAnchor+ 3*carteTileSize, carteTopAnchor+ 4*carteTileSize, null);
    }    
    
    // dessin de la carte du jeu
    private void paintcarte(Canvas canvas) {
        Bitmap marron = marron1.createScaledBitmap (marron1, 20, 20, true);
        Bitmap red = red1.createScaledBitmap (red1, 20, 20, true);
        Bitmap fond = fond1.createScaledBitmap (fond1, 20, 20, true);
        Bitmap black = black1.createScaledBitmap (black1, 20, 20, true);
        Bitmap blue = red1.createScaledBitmap (blue1, 20, 20, true);
        Bitmap green = fond1.createScaledBitmap (green1, 20, 20, true);

    	for (int i=0; i< carteHeight; i++) {
            for (int j=0; j< carteWidth; j++) {
                switch (carte[i][j]) {

                    case CST_zone:
                    	//canvas.drawBitmap(zone[currentStepZone],carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                    case CST_espace:
                    	canvas.drawBitmap(espace,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                    case CST_black:
                    canvas.drawBitmap(black,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                    case CST_marron:
                        canvas.drawBitmap(marron,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                    case CST_red:
                        canvas.drawBitmap(red,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                    case CST_green:
                        canvas.drawBitmap(green,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                    case CST_blue:
                        canvas.drawBitmap(blue,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                }
            }
        }
    }







    // dessin de la figure rouge
   private void paintred(Canvas canvas) {
       Bitmap red = red1.createScaledBitmap (red1, 20, 20, true);
        for (int i=0; i< 3; i++) {
            canvas.drawBitmap(red,xred+tabred[i][0]*carteTileSize, yred + tabred[i][1]*carteTileSize, null);

        }


    }



    private void paintgreen(Canvas canvas) {
        Bitmap green = green1.createScaledBitmap (green1, 20, 20, true);

        for (int i=0; i< 7; i++) {
            canvas.drawBitmap(green,xgreen+tabgreen[i][0]*carteTileSize, ygreen + tabgreen[i][1]*carteTileSize, null);
        }
        refxgreen=xgreen+tabgreen[0][0]*carteTileSize;
        refygreen=ygreen- tabgreen[0][1]*carteTileSize;
    }



    private void paintblue(Canvas canvas) {
        Bitmap blue = blue1.createScaledBitmap (blue1, 20, 20, true);

        for (int i=0; i< 7; i++) {
            canvas.drawBitmap(blue,xblue+tabblue[i][0]*carteTileSize, yblue + tabblue[i][1]*carteTileSize, null);
        }
        refxblue=xblue+tabblue[0][0]*carteTileSize;
        refyblue=yblue- tabblue[0][1]*carteTileSize;

    }




    private void paintgrena(Canvas canvas) {
        Bitmap grena = grena1.createScaledBitmap (grena1, 20, 20, true);
        for (int i=0; i< 7; i++) {
            canvas.drawBitmap(grena,xgrena+tabgrena[i][0]*carteTileSize, ygrena + tabgrena[i][1]*carteTileSize, null);
        }
        refxgrena=xgrena+tabgrena[0][0]*carteTileSize;
        refygrena=ygrena- tabgrena[0][1]*carteTileSize;

    }



    private void paintmarron(Canvas canvas) {
        Bitmap marron = marron1.createScaledBitmap (marron1, 20, 20, true);
        for (int i=0; i< 7; i++) {
            canvas.drawBitmap(marron,xmarron+tabmarron[i][0]*carteTileSize, ymarron + tabmarron[i][1]*carteTileSize, null);

        }
        refxmarron=xmarron+tabmarron[0][0]*carteTileSize;
        refymarron=ymarron- tabmarron[0][1]*carteTileSize;
    }




    private void paintblack(Canvas canvas) {
        Bitmap black = black1.createScaledBitmap(black1, 20, 20, true);
        for (int i = 0; i < 7; i++) {
            canvas.drawBitmap(black, xblack + tabblack[i][0] * carteTileSize, yblack + tabblack[i][1] * carteTileSize, null);
        }
        refxblack=xblack+tabblack[0][0]*carteTileSize;
        refyblack=yblack- tabblack[0][1]*carteTileSize;

    }

    private void paintviolet(Canvas canvas) {
        Bitmap violet = violet1.createScaledBitmap (violet1, 20, 20, true);
        for (int i=0; i< 7; i++) {
            canvas.drawBitmap(violet,xviolet+tabviolet[i][0]*carteTileSize, yviolet + tabviolet[i][1]*carteTileSize, null);
        }

    }

    // permet d'identifier si la partie est gagnee (tous les diamants � leur place)
   private boolean isWon() {
        for (int i=0; i< 4; i++) {
           // if (!IsCell(diamants[i][1], diamants[i][0], CST_zone)) {
                return false;
            //}
        }
        return true;
    }
    
    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)
    private void nDraw(Canvas canvas) {
		canvas.drawRGB(224,192,104);

    	if (isWon()) {
        	paintcarte(canvas);
        	paintwin(canvas);
            Intent i = new Intent().setClass(getContext(), P8_cassetete2.class);
            getContext().startActivity(i);
        } else {


            paintcarte(canvas);
            paintred(canvas);
            paintgreen(canvas);
            paintblue(canvas);
            paintmarron(canvas);
            paintgrena(canvas);
            paintblack(canvas);
            paintviolet(canvas);

        }    	   	
        
    }
    
    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    	Log.i("-> FCT <-", "surfaceChanged "+ width +" - "+ height);
    	initparameters();
    }

    public void surfaceCreated(SurfaceHolder arg0) {
    	Log.i("-> FCT <-", "surfaceCreated");    	        
    }

    
    public void surfaceDestroyed(SurfaceHolder arg0) {
    	Log.i("-> FCT <-", "surfaceDestroyed");    	        
    }    

    /**
     * run (run du thread cr��)
     * on endort le thread, on modifie le compteur d'animation, on prend la main pour dessiner et on dessine puis on lib�re le canvas
     */
    public void run() {
    	Canvas c = null;
        while (in) {
            try {
                cv_thread.sleep(40);
                currentStepZone = (currentStepZone + 1) % maxStepZone;
                try {
                    c = holder.lockCanvas(null);
                    nDraw(c);
                } finally {
                	if (c != null) {
                		holder.unlockCanvasAndPost(c);
                    }
                }
            } catch(Exception e) {
            	Log.e("-> RUN <-", "PB DANS RUN");
            }
        }
    }
    
    // verification que nous sommes dans l'ecran
   private boolean IsOut(int x, int y, int xf, int yf) {
        if ((x < 0) || (xf > 320)) {
            return true;
        }
        if ((yf < 0) || (y > 458)) {
            return true;
        }
        return false;
    }

    //controle de la valeur d'une cellule
    private boolean IsCell(int x, int y, int mask) {
        if (carte[y][x] == mask) {
            return true;
        }
        return false;
    }


    public int place(int x){
        int y;
        if((x % carteTileSize)<=10) {
            y = x / carteTileSize;
            x = y * carteTileSize;
        }
        else
        {
            y = x / carteTileSize;
            x = (y+1) * carteTileSize;

        }
        return x;
    }


    // fonction permettant de recuperer les evenements tactiles

    public boolean IsTouch(int x, int y, int xfigure, int yfigure, int [][] tab, int indice){

        int i,k=0,f=0;
        for(i=0;i<indice-1;i++){
            if(tab[i][1]==tab[i+1][1]){
                k=tab[i+1][1]+1;
            }
            else
                k=tab[i][1]+1;

            if (tab[i][0]==tab[i+1][0]){
                f=tab[i][0]+1;
            }
            else
                f=tab[i+1][0];

            if((x>=(xfigure+tab[i][0]*carteTileSize))&&(x<=(xfigure+f*carteTileSize))&&(y>=(yfigure+tab[i][0]*carteTileSize))&&(y<=(yfigure+k*carteTileSize)))
                return true;
        }
        if((x>=(xfigure+tab[indice-1][0]*carteTileSize))&&(x<=(xfigure+(tab[indice-1][0]+1)*carteTileSize))&&(y>=(yfigure+tab[indice-1][1]*carteTileSize))&&(y<=(yfigure+(tab[indice-1][1]+1)*carteTileSize)))
            return true;

        return false;
    }


    @TargetApi(Build.VERSION_CODES.FROYO)
    public boolean onTouchEvent (MotionEvent event) {
        Log.i("-> FCT <-", "onTouchEvent: "+ event.getX());

        int positionX=(int)event.getX();
        int positionY=(int)event.getY();

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:

                /*positionX >= (refxred -10)  && positionX <= (refxred + lred * carteTileSize) + 30 && positionY >= (refyred ) - 10 && positionY <= (refyred - (hred-1) * carteTileSize) + 10*/

                if (IsTouch(positionX,positionY,xred,yred,tabred,3))
                    touch1 = true;
                else touch1 = false;

                //positionX >= (refxgreen -10)  && positionX <= (xgreen + lgreen * carteTileSize) + 20 && positionY >= (ygreen - tabgreen[0][1] * carteTileSize) - 30 && positionY <= (ygreen - tabgreen[2][1] * carteTileSize) + 100



                if (IsTouch(positionX,positionY,xgreen,ygreen,tabgreen,7))
                    touch2 = true;
                else touch2 = false;


                //positionX >= (xblue + tabblue[0][0] * carteTileSize) - 10 && positionX <= (xblue + tabblue[6][0] * carteTileSize)+50  && positionY >= (yblue - tabblue[0][1] * carteTileSize) - 10 && positionY <= (yblue - tabblue[6][1] * carteTileSize)+50

                if ( IsTouch(positionX,positionY,xblue,yblue,tabblue,7))

                    touch3 = true;

                else touch3 = false;

                //positionX >= (xmarron + tabmarron[0][0] * carteTileSize) - 10 && positionX <= (xmarron + tabmarron[6][0] * carteTileSize)+50  && positionY >= (ymarron - tabmarron[0][1] * carteTileSize) - 30 && positionY <= (ymarron - tabmarron[6][1] * carteTileSize)+50



                if ( IsTouch(positionX,positionY,xmarron,ymarron,tabmarron,7))

                    touch4 = true;
                else touch4 = false;


                //positionX >= (xgrena + tabgrena[0][0] * carteTileSize) - 30 && positionX <= (xgrena + tabgrena[6][0] * carteTileSize)+50  && positionY >= (ygrena - tabgrena[0][1] * carteTileSize) - 30 && positionY <= (ygrena - tabgrena[6][1] * carteTileSize)+50


                if (IsTouch(positionX,positionY,xgrena,ygrena,tabgrena,7))

                    touch5 = true;
                else touch5 = false;

                //positionX >= (xblack + tabblack[0][0] * carteTileSize) - 10 && positionX <= (xblack + tabblack[6][0] * carteTileSize)+50  && positionY >= (yblack - tabblack[0][1] * carteTileSize) - 10 && positionY <= (yblack - tabblack[6][1] * carteTileSize)+50


                if (IsTouch(positionX,positionY,xblack,yblack,tabblack,7))

                    touch6 = true;
                else touch6 = false;

                //positionX >= (xviolet + tabviolet[0][0] * carteTileSize) - 10 && positionX <= (xviolet + tabviolet[6][0] * carteTileSize)+50  && positionY >= (yviolet - tabviolet[0][1] * carteTileSize) - 10 && positionY <= (yviolet - tabviolet[6][1] * carteTileSize)+50


                if (IsTouch(positionX,positionY,xviolet,yviolet,tabviolet,7) )

                    touch7 = true;
                else touch7 = false;


                break;
            case MotionEvent.ACTION_MOVE:
                if (touch1) {
                    tmpx = positionX;
                    tmpy = positionY;
                    xfred=tmpx+lred*carteTileSize;
                    yfred=tmpy+(hred-1)*carteTileSize;
                    if (! IsOut(tmpx,yfred,xfred,tmpy)) {
                        xred = place(positionX);
                        yred = place(positionY);

                    }

                }

                if (touch2) {
                    tmpx = positionX;
                    tmpy = positionY;
                    xfgreen=tmpx+lgreen*carteTileSize;
                    yfgreen=tmpy+(hgreen-1)*carteTileSize;
                    if (! IsOut(tmpx,yfgreen,xfgreen,tmpy)) {


                        xgreen = place(positionX);
                        ygreen = place(positionY);
                    }

                }

                if (touch3) {

                    tmpx = positionX;
                    tmpy = positionY;
                    xfblue=tmpx+lblue*carteTileSize;
                    yfblue=tmpy-(hblue-1)*carteTileSize;
                    if (! IsOut(tmpx,tmpy,xfblue,yfblue)) {
                        xblue = place(positionX);
                        yblue = place(positionY);
                    }
                }

                if (touch4) {
                    Log.e("lol","je suis la");
                    Log.i("lol","je suis la"+positionX);
                    Log.i("lol","je suis la"+positionY);
                    tmpx = positionX;
                    tmpy = positionY;
                    xfmarron=tmpx+lmarron*carteTileSize;
                    yfmarron=tmpy-(1)*carteTileSize;
                    if (! IsOut(tmpx,tmpy+20,xfmarron,yfmarron)) {
                        xmarron = place(positionX);
                        ymarron = place(positionY);
                        Intent i = new Intent().setClass(getContext(), P8_cassetete2.class);
                        getContext().startActivity(i);

                    }
                }

                if (touch5) {
                    tmpx = positionX;
                    tmpy = positionY;
                    xfgrena=tmpx+lgrena*carteTileSize;
                    yfgrena=tmpy+(4-1)*carteTileSize;
                    if (! IsOut(tmpx,yfgrena,xfgrena,tmpy)) {
                        xgrena = place(positionX);
                        ygrena =place(positionY);
                    }
                }

                if (touch6) {
                    tmpx = positionX;
                    tmpy = positionY;
                    xfblack=tmpx+lblack*carteTileSize;
                    yfblack=tmpy+(hblack-1)*carteTileSize;
                    if (! IsOut(tmpx,yfblack,xfblack,tmpy)) {
                        xblack = place(positionX);
                        yblack = place(positionY);
                    }
                }

                if (touch7) {
                    tmpx = positionX;
                    tmpy = positionY;
                    xfviolet=tmpx+lviolet*carteTileSize;
                    yfviolet=tmpy+(hviolet-2)*carteTileSize;
                    if (! IsOut(tmpx,yfviolet,xfviolet,tmpy-20)) {
                        xviolet = place(positionX);
                        yviolet = place(positionY);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                touch1 = false;
                touch2 = false;
                touch3 = false;
                touch4 = false;
                touch5 = false;
                touch6 = false;
                touch7 = false;
                break;



        }


        invalidate();




        return true;
    }

}
