package main.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import model.Mascota;
import model.Persona;

/**
 *
 * @author benjamin
 */
public class ConvertirMapAObject {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Mascota mascota = new Mascota(23, "chiripi", "perro");
        Map<String, Object> map = new HashMap();
        map.put("id", 7L);
        map.put("nombre", "benjamin");
        map.put("correo", "benjamin@benjamin.com");
        map.put("mascota", mascota);
        Persona p = new Persona(7L, "vivi", "vivi@benjamin.com", 23);
        System.out.println(makeObject(Persona.class, map));

    }

    /**
     * crea un objeto POJO a partir de un Map.
     * @param <T>
     * @param classType
     * @param map
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public static <T> T makeObject(Class<T> classType, Map<String, Object> map)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Method[] entityMethods = classType.getMethods();
        T entityInstance = classType.newInstance();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String prop = entry.getKey();
            for (Method m : entityMethods) {
                if (m.getName().equalsIgnoreCase("set".concat(prop))) {
                    for (Class typo : m.getParameterTypes()){
                        m.invoke(entityInstance, typo.cast(entry.getValue()));
                    }
                    break;
                }
            }
        }

        return entityInstance;
    }
    
}
