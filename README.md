# ANDROID BASICS
Cheatsheat de los basicos para el desarrollo de aplicaciones Android con un ejemplo de Login y RecyclerView

## Añadir OnClick
```java
    Buttom btnLogin;

    btnLogin = findViewById(R.id.btnLogin);

    btnLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
    });
```

## Iniciar una actividad desde otra
```java
Intent intent = new Intent(this, NombreDeLa.class);
startActivity(intent);
```
Nota: `this` es el contexto de la actividad actual, si se estruviera en un fragmene para obtener el contexto de la actividad se sustituiria por `getApplicationContext()`

## Paso de variables entre actividades
### En la actividad actual
```java
intent.putExtra("user", user);
```

### Capturar los datos en la siguiente actividad 
```java
String user = intent.getStringExtra("user");
```

## Animaciones
1. Crear una carpeta nueva en `res` llamada `anim`
2. Dentro de esa carpeta crear un archivo `.XML` con el siguiente esquema
   ``` XML
    <?xml version="1.0" encoding="utf-8"?>
    <set xmlns:android="http://schemas.android.com/apk/res/android">

    <!--  Rota una vuelta al incio durante un segundo -->
    <rotate
        android:fromDegrees="0"
        android:toDegrees="360"
        android:duration="1000"
        android:startOffset="0"
        android:pivotX="50%"
        android:pivotY="50%"
        />

    <!-- Empieza a mover el elemnto despues de un segundo -->
    <translate
        android:fromXDelta="0"
        android:toXDelta="1000"
        android:duration="300"
        android:startOffset="1000"
        />

    </set>
   ```
3. En el controlador `.java` del Activity
   ``` java
    Animation anim = AnimationUtils.loadAnimation(this, R.anim.animation);
    elementoAAnimar.startAnimation(anim);
   ```
   
## Base de datos
```java
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "demo.db";

    public BD(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) throws SQLiteException{
        db.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLiteException{
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void insertUser(String username, String password) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        db.insert("users", null, contentValues);
        db.close();
    }

    public boolean checkUser(String username) throws SQLiteException{
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public boolean checkUsernamePassword(String username, String password) throws SQLiteException{
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public void deleteUser(String username) throws SQLiteException{
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users", "username = ?", new String[]{username});
        db.close();
    }

    public void updateUser(String username, String password) throws SQLiteException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        db.update("users", contentValues, "username = ?", new String[]{username});
        db.close();
    }

}
```

## Importar fotos
Arrastrar la foto desde el explorador de archivos a la carpeta `drawable` sin tener mayusculas, simbolos raros (sólo barras bajas), ni numeros.

## Cambiar Activity de incio
Añadir esto en el `AndroidManifest.xml` en el activity que se quiera hacer el primero al ejecutar desde 0 
```xml
<intent-filter>
    <action android:name="android.intent.action.MAIN" />
    <category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
```
Por ejemplo:
``` xml
<activity
    android:name=".ui.AnimationActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>

    <meta-data
        android:name="android.app.lib_name"
        android:value="" />
</activity>
```

## RecyclerView
1. Añadir un RecyclerView en el fichero de la interfaz
2. En el `.java` del controlador (`ActiviyRecyclerView.java`)
    1.  Declarar un atributode de tipo RecyclerView y enlazarlo por ID al RecyclerView de la interfaz
    2.  Declarar un `ArrayList<?>` del tipo de datos que le pasaremos al RecyclerView
3. Crear un `.xml` nuevo en la carpeta `res\layout` que será el el formato que tomarán los datos que se mostraran en el RecyclerView, por ejemplo:
   ```xml
   <!-- tarjeta.xml -->
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/linear"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:backgroundTint="@color/red"
    android:orientation="horizontal">


    <TextView
        android:id="@+id/txtAlumno"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="TextView" />

    <TextView
        android:id="@+id/txtApellido"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="TextView"
        android:textAlignment="textEnd" />
    </LinearLayout>
   ```
4. Crear un `.java` que, en este caso llamaremos, `AdapterDatos.java` que herede de `RecyclerView.Adapter<AdapterDatos.ViewHolder>` y que se encargará de insertar la informacion en las tarjetas dentro del RecyclerView. 
5. Aceptamos todas las importaciones y arreglos de warnings
6. Añades el codigo necesario (esta todo lo necesario comentado)
    ```java
    // BASEDATOS.java
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.repaso.models.Alumno;

    import java.util.ArrayList;

    public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolder>{
        ArrayList<Alumno> listaNombres;

        // Constructor, se le pasa la lista de nombres
        public AdapterDatos(ArrayList<Alumno> listaNombres) {
            this.listaNombres = listaNombres;
        }

        // Crea las "tarjetas" que se van a mostrar en el RecyclerView
        @NonNull
        @Override
        public AdapterDatos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Se crea la vista de la tarjeta
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta, parent, false);

            // Se crea el ViewHolder (la clase interna de mas abajo) y se le pasa la vista
            return new ViewHolder(view);
        }

        // Asigna los datos a cada tarjeta (ViewHolder) con los datos de la lista
        @Override
        public void onBindViewHolder(@NonNull AdapterDatos.ViewHolder holder, int position) {
            holder.asignarDatos(listaNombres.get(position));
        }

        // Devuelve el tamaño de la lista
        @Override
        public int getItemCount() {
            return listaNombres.size();
        }

        // CLASE DEL VIEWHOLDER, EL CONTROLADOR DE LAS TARJETAS
        public class ViewHolder extends RecyclerView.ViewHolder {
            // Se declaran los elementos de la tarjeta
            TextView nombre;
            TextView apellido;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                // Se asignan los elementos de la tarjeta
                nombre = itemView.findViewById(R.id.txtAlumno);
                apellido = itemView.findViewById(R.id.txtApellido);

                // Se le asigna un evento al hacer click en la tarjeta
                this.itemView.setOnClickListener(v -> Toast.makeText(itemView.getContext(), "Click en " + nombre.getText(), Toast.LENGTH_SHORT).show());
            }

            // Se asignan los datos a los elementos de la tarjeta
            public void asignarDatos(Alumno alumno) {
                nombre.setText(alumno.getNombre());
                apellido.setText(alumno.getApellido());
            }
        }
    }
    ```
7. De vuelta en el `OnCreate(Bundle savedInstanceState)` del `ActiviyRecyclerView.java` de la vista añadir el siguiente codigo para cargar el RecyclerView
    ```java
    // RECYCLER VIEW
    // 1. Crear el ArrayList
    listaNombres = new ArrayList<>();
    listaNombres.add(new Alumno("Juan", "Perez"));
    listaNombres.add(new Alumno("Maria", "Gomez"));
    listaNombres.add(new Alumno("Pedro", "Gonzalez"));
    listaNombres.add(new Alumno("Jose", "Lopez"));
    listaNombres.add(new Alumno("Luis", "Martinez"));

    // 2. Añadir el linear layout al recycler para que muestre los datos
    recycler.setLayoutManager(new LinearLayoutManager(this));

    // 3. Crear el adapter y pasarle el ArrayList
    AdapterDatos adapter = new AdapterDatos(listaNombres);

    // 4. Asignar el adapter al recycler
    recycler.setAdapter(adapter);
    ```