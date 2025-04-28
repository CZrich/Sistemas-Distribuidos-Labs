import random

def berkeley(tiempos_locales):
    tiempo_servidor = tiempos_locales[0] # Supondremos que el primer nodo sera el nodo servidor
    print(f"Servidor inicia con tiempo : {tiempo_servidor:.2f}")

    # Ahora el servidor pide los timepos de todos los nodos cliente
    diferencia_tiempos = []

    for t in tiempos_locales:
        diferencia = t - tiempo_servidor
        diferencia_tiempos.append(diferencia)

    # Calculamos el promedio de las diferencias
    diferencia_promedio = sum(diferencia_tiempos)/len(diferencia_tiempos)
    print(f"Diferencia promedio : {diferencia_promedio:.2f}")

    # Calculo del nuevo tiempo

    tiempo_sincronizado = tiempo_servidor + diferencia_promedio

    # El servidor envia a cada uno cuanto debe ajustar el reloj

    ajustes = []

    for t in tiempos_locales:
        ajuste = tiempo_sincronizado - t
        ajustes.append(ajuste)


    return tiempo_sincronizado, ajustes

tiempos_locales = [100.0, 102.3, 98.7, 101.5, 99.8] 

tiempo_sincro, ajustes = berkeley(tiempos_locales)

print("\nResultados:")
for i, (local, adj) in enumerate(zip(tiempos_locales, ajustes)):
    print(f"Reloj {i}: Hora inicial = {local:.2f}, Ajuste = {adj:+.2f},Hora final = {local + adj:.2f}")
