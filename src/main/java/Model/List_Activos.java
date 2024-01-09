package Model;


import java.util.ArrayList;
import java.util.List;

public class List_Activos {
    static List<Activo> list = new ArrayList<>();
    public List<Activo> getList() {
        return list;
    }
    public void setList(List<Activo> list){
        list = list;
    }
    public void agregar (Activo ins){list.add(ins);}

    public Activo obtener(String str) {
        for (Activo object : list) {
            if (object.getCodigo().equals(str)) {
                return object;
            }
        }
        return null;
    }
}