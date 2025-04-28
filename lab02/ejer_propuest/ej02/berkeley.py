def berkeley(tiempos_locales):
    #T0
    tiempo_servidor = tiempos_locales[0]
    print(f"1. Tiempo (Servidor) T0 : {tiempo_servidor:.3f}")
    print(f"\tTs0 : {tiempos_locales[0]:.3f}")
    for i in range(1, len(tiempos_locales)):
        
        print(f"\tTc{i} : {tiempos_locales[i]:.3f}")
    # El Servidor difundira T0 a todos los clientes
    print("2. Envio de T0 a todos los clientes (C1,C2,C3)")
    print("3. El tiempo de envio son de 0.005 unidades de tiempo, esto afectara el reloj de todos")
    tiempos_locales = envio_delay(tiempos_locales)
    print("4. +0.005, Nuevos tiempos: ")
    print(f"\tTs0 : {tiempos_locales[0]:.3f}")
    for i in range(1, len(tiempos_locales)):
        print(f"\tTc{i} : {tiempos_locales[i]:.3f}")

    # Se calcularan las diferencias
    print("5. Calculo de diferencias con T0 (enviado en Paso #2): ")
    diferencias = []
    for i in range(len(tiempos_locales)):
        diferencia = tiempos_locales[i] - tiempo_servidor
        diferencias.append(diferencia)
    # Imprime las diferencias
    for i in range(1, len(tiempos_locales)):
        print(f"\tDif. c{i} : {diferencias[i]:.3f}")
    print("6. Se comunica las diferencias al Servidor")
    print("7. Delay al enviar... +0.005 para todos")
    tiempos_locales = envio_delay(tiempos_locales)
    # La respuesta de cada cliente llegara en T1
    tiempo_servidor_1 = tiempos_locales[0]
    print("8. Nuevos tiempos")
    
    print(f"\tTs0 : {tiempos_locales[0]:.3f}")
    for i in range(1, len(tiempos_locales)):
        print(f"\tTc{i} : {tiempos_locales[i]:.3f}")
    print("9. Ajuste de Diferencias")
    # Para el calculo de las diferencias se usa la formula
    # Di' = Di - (T1 - T0) / 2
    ajuste_diferencias = []
    for i in range(len(diferencias)):
        ajuste = diferencias[i] - (tiempo_servidor_1 - tiempo_servidor) / 2
        ajuste_diferencias.append(ajuste)
    for i in range(1, len(tiempos_locales)):
        print(f"\tAjuste Dif. c{i} : {ajuste_diferencias[i]:.3f}")
    print("10. Calculo de la Diferencia media")
    diferencia_media = sum(ajuste_diferencias)/len(tiempos_locales)
    print(f"\tDif. media : {diferencia_media:.3f}")
    print("11. Ajuste del reloj (Servidor) aumentando la diferencia media")
    tiempos_locales[0] = tiempos_locales[0] + diferencia_media
    print(f"\tNuevo Reloj del Servidor : {tiempos_locales[0]:.3f}")
    print("12. Nuevo calculo de ajuste para cada cliente")
    # Usando Ai = D (media) - Ajuste
    ajuste_finales = []
    for i in range(len(tiempos_locales)):
        ajuste_final = diferencia_media - ajuste_diferencias[i]
        ajuste_finales.append(ajuste_final)
    #Este es un ajuste que se hace porque se hacen calculos innecesarios para el tiempo de Servidor
    ajuste_finales[0] = ajuste_finales[0] * 0
    for i in range(1, len(tiempos_locales)):
        print(f"\tAjuste final. c{i} : {ajuste_finales[i]:.3f}")
    print("13. Envio del ajuste final a cada cliente")
    print("14. Delay al enviar... +0.005 para todos")
    tiempos_locales = envio_delay(tiempos_locales)
    print(f"\tTs0 : {tiempos_locales[0]:.3f}")
    for i in range(1, len(tiempos_locales)):
        print(f"\tTc{i} : {tiempos_locales[i]:.3f}")
    print("15. Aplicando los ajustes finales")
    for i in range(len(tiempos_locales)):
        tiempos_locales[i] = tiempos_locales[i] + ajuste_finales[i]
    print(f"\tTs0 : {tiempos_locales[0]:.3f}")
    for i in range(1, len(tiempos_locales)):
        print(f"\tTc{i} : {tiempos_locales[i]:.3f}")
    
# Este metodo simula el tiempo en que el servidor o los clientes envien los mensajes
# Por temas de simplificación SUPONEMOS que todos los mensajes llegan al mismo tiempo
# Aunque la realidad es diferente
def envio_delay(tiempos_locales):
    for i in range(len(tiempos_locales)):
        tiempos_locales[i] += retraso
    return tiempos_locales


tiempos_locales = [10.000, 9.998, 10.001, 10.005, 10.002, 10.003] 
retraso = 0.005 # El tiempo que demorara en enviar sera de 5 unidades de tiempo


print("Algoritmo Barkeley")
print(" ")
berkeley(tiempos_locales)
