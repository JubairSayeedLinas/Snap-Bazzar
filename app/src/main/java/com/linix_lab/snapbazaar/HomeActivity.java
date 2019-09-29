package com.linix_lab.snapbazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.linix_lab.snapbazaar.Model.Products;
import com.linix_lab.snapbazaar.Prevalent.Prevalent;
import com.linix_lab.snapbazaar.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView,recyclerView1;
    RecyclerView.LayoutManager layoutManager,layoutManager1;
    Button buttonLaptops,buttonAll,buttonTshirt,buttonFemaleDress,buttonMobile,buttonShoes,buttonSportsTShirts,buttonGlasses,buttonWalletsBagsPurses,buttonHeadPhonesHandFree,buttonWatches,buttonHatsCaps,buttonSweathers;
    String category="All";
    int countNum=0;


    private String type="";

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private String[] urls = new String[] {"https://static.ajkerdeal.com/img/banner/mobilehometop/offer204754545454225.jpg", "https://static.ajkerdeal.com/img/banner/mobilehometop/offer22147524189665656329.jpg", "https://static.ajkerdeal.com/img/banner/mobilehometop/offer9954521.jpg",
            "https://static.ajkerdeal.com/img/banner/mobilehometop/offer13796465122.jpg", "https://static.ajkerdeal.com/img/banner/mobilehometop/offer9954521.jpg", "https://static.ajkerdeal.com/img/banner/mobilehometop/offer22147524189665656329.jpg"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



       /*Intent intent=getIntent();
       Bundle bundle=intent.getExtras();
       if(bundle!=null){
           type=getIntent().getExtras().get("").toString();
       }*/

        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView1=findViewById(R.id.recycler_menu1);
        recyclerView.setHasFixedSize(true);
        recyclerView1.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView1.setNestedScrollingEnabled(false);

        layoutManager=new LinearLayoutManager(this);
        layoutManager1=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView1.setLayoutManager(layoutManager1);



        recyclerView.setNestedScrollingEnabled(false);
        recyclerView1.setNestedScrollingEnabled(false);


        buttonLaptops=findViewById(R.id.buttonLaptops);
        buttonAll=findViewById(R.id.buttonAll);
        buttonTshirt=findViewById(R.id.buttonTshirt);
        buttonFemaleDress=findViewById(R.id.buttonFemaleDress);
        buttonMobile=findViewById(R.id.buttonMobile);
        buttonGlasses=findViewById(R.id.buttonGlasses);
        buttonHatsCaps=findViewById(R.id.buttonHatsCaps);
        buttonHeadPhonesHandFree=findViewById(R.id.buttonHeadPhonesHandFree);
        buttonShoes=findViewById(R.id.buttonShoes);
        buttonWatches=findViewById(R.id.buttonWatches);
        buttonSweathers=findViewById(R.id.buttonSweathers);
        buttonWalletsBagsPurses=findViewById(R.id.buttonWalletsBagsPurses);
        buttonSportsTShirts=findViewById(R.id.buttonSportsTShirts);


        Paper.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hedaderView=navigationView.getHeaderView(0);
        TextView userNameTextView= hedaderView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView=hedaderView.findViewById(R.id.user_profile_image);


        /*if(!type.equals("Admin")){
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }*/




        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="All";
                onStart();
            }
        });


        buttonLaptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Laptops";
                onStart();
            }
        });

        buttonTshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="tShirts";
                onStart();
            }
        });

        buttonSportsTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Sports tShirts";
                onStart();
            }
        });

        buttonFemaleDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Female Dresses";
                onStart();
            }
        });
        buttonSweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Sweathers";
                onStart();
            }
        });

        buttonGlasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Glasses";
                onStart();
            }
        });

        buttonHatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Hats Caps";
                onStart();
            }
        });

        buttonWalletsBagsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Wallets Bags Purses";
                onStart();
            }
        });

        buttonShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Shoes";
                onStart();
            }
        });

        buttonHeadPhonesHandFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="HeadPhones HandFree";
                onStart();
            }
        });

        buttonWatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Watches";
                onStart();
            }
        });
        buttonMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Mobile Phones";
                onStart();
            }
        });



        init();


    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(HomeActivity.this,urls));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = urls.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();



        if(category.equals("All")) {
            FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(ProductsRef.orderByChild("category").limitToFirst(13), Products.class)
                    .build();


            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                    holder.txtProductName.setText(model.getPname());
                    //holder.txtProductDescription.setText(model.getDescription());
                    holder.txtProductPrice.setText("Price " + model.getPrice() + "৳");
                    Picasso.get().load(model.getImage()).into(holder.imageView);


                    //click on the product to go to product details
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                                Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);




                        }
                    });


                }

                @NonNull
                @Override
                public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                    return new ProductViewHolder(view);
                }
            };

            recyclerView.setAdapter(adapter);
            adapter.startListening();



            FirebaseRecyclerOptions<Products> options1 = new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(ProductsRef.orderByChild("category").limitToLast(13), Products.class)
                    .build();


            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter1 = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options1) {
                @Override
                protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                    holder.txtProductName.setText(model.getPname());
                    //holder.txtProductDescription.setText(model.getDescription());
                    holder.txtProductPrice.setText("Price " + model.getPrice() + "৳");
                    Picasso.get().load(model.getImage()).into(holder.imageView);



                    //click on the product to go to product details
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", model.getPid());
                            startActivity(intent);

                        }
                    });


                }

                @NonNull
                @Override
                public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                    ProductViewHolder holder = new ProductViewHolder(view);
                    return holder;
                }
            };

            recyclerView1.setAdapter(adapter1);
            adapter1.startListening();


            } else {
            FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(ProductsRef.orderByChild("category").equalTo(category).limitToFirst(5), Products.class)
                    .build();


            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                    holder.txtProductName.setText(model.getPname());
                    //holder.txtProductDescription.setText(model.getDescription());
                    holder.txtProductPrice.setText("Price " + model.getPrice() + "৳");
                    Picasso.get().load(model.getImage()).into(holder.imageView);

                    //click on the product to go to product details


                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                                Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);


                        }
                    });


                }

                @NonNull
                @Override
                public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                    ProductViewHolder holder = new ProductViewHolder(view);
                    return holder;
                }
            };

            recyclerView.setAdapter(adapter);
            adapter.startListening();


            FirebaseRecyclerOptions<Products> options1 = new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(ProductsRef.orderByChild("category").equalTo(category).limitToLast(5), Products.class)
                    .build();


            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter1 = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options1) {
                @Override
                protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                    holder.txtProductName.setText(model.getPname());
                    //holder.txtProductDescription.setText(model.getDescription());
                    holder.txtProductPrice.setText("Price " + model.getPrice() + "৳");
                    Picasso.get().load(model.getImage()).into(holder.imageView);

                    //click on the product to go to product details
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", model.getPid());
                            startActivity(intent);

                        }
                    });


                }

                @NonNull
                @Override
                public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                    ProductViewHolder holder = new ProductViewHolder(view);
                    return holder;
                }
            };

            recyclerView1.setAdapter(adapter1);
            adapter1.startListening();
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            Intent intent=new Intent(HomeActivity.this,CartActivity.class);
            startActivity(intent);



        } else if (id == R.id.nav_search) {
            Intent intent=new Intent(HomeActivity.this,SearchProductsActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_feedback) {
            Intent intent=new Intent(HomeActivity.this,FeedbackActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            Intent intent=new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Paper.book().destroy();

            Intent intent=new Intent(HomeActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
