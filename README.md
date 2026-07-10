# Super Mine Mod (Fabric 1.21.1)

Netherite ketmon (pickaxe) va belkurak (shovel) uchun 6x6 Super Mine rejimi.

## Nima qiladi
- **B** tugmasini bossangiz (default), Super Mine rejimi yoqiladi/o'chiriladi.
  Tugmani **Options -> Controls -> Super Mine Mod** bo'limidan istalgan tugmaga o'zgartirish mumkin.
- Rejim yoqilganda, faqat qo'lingizda **Netherite Pickaxe** yoki **Netherite Shovel** bo'lsa,
  bitta blok buzganingizda uning atrofidagi jami 6x6 (36 blok) maydon ham birga buziladi.
- Rejim o'chirilganda hammasi oddiy tarzda ishlайdi (faqat 1 blok buziladi).
- Chatda yashil/qizil xabar chiqadi: "Yoqildi" / "O'chirildi".

## Talablar
- Minecraft Java 1.21.1
- Fabric Loader >= 0.16.9
- Fabric API (1.21.1 uchun) — bu mod unga bog'liq, alohida o'rnatilishi kerak

## ENG OSON YO'L: GitHub Actions orqali avtomatik jar olish (kod yozish/IntelliJ shart emas)

Bu loyihada `.github/workflows/build.yml` fayli bor — GitHub'ning bepul serverlarida
avtomatik ravishda jar'ni build qilib beradi, senga faqat yuklab olish qoladi.

1. https://github.com saytida akkaunt oching (agar yo'q bo'lsa).
2. Yangi bo'sh repository yarating (masalan nomi: `supermine`, Public yoki Private —
   farqi yo'q).
3. Shu zip'dagi **hamma fayllarni** (papkalar bilan birga) o'sha repoga yuklang:
   - Eng oson: GitHub'dagi repo sahifasida "Add file" -> "Upload files" tugmasini
     bosib, zip ichidagi hamma fayl/papkalarni tortib tashlang (drag & drop),
     so'ng pastda "Commit changes" tugmasini bosing.
4. Fayllar yuklangandan keyin, repo ichida **Actions** bo'limiga o'ting.
5. "Build Mod Jar" degan workflow avtomatik ishga tushadi (agar ishga tushmasa,
   "Run workflow" tugmasini bosing).
6. Bir necha daqiqadan so'ng (yashil galochka chiqqach) o'sha workflow run'ini
   oching, pastda **Artifacts** bo'limida `supermine-jar` deb nomlangan faylni
   ko'rasiz — shuni bosib yuklab oling. Ichida tayyor `.jar` fayl bo'ladi.
7. Yuklab olgan jar'ni `.minecraft/mods` papkasiga tashlang (Fabric API jar bilan
   birga).

Shu bilan tamom — kompyuteringizga IntelliJ yoki Gradle o'rnatish shart emas,
hammasi GitHub serverida bepul ishlaydi.

## Qanday build qilinadi (compile) — qo'lda, kompyuterda
Sandbox muhitida menda to'g'ridan-to'g'ri Minecraft/Fabric kutubxonalariga internet
orqali ulanish imkoni yo'q (faqat cheklangan domenlarga chiqish bor), shuning uchun
tayyor .jar fayl emas, balki to'liq source code loyihasini beryapman. Buni build
qilish uchun:

1. Loyihani kompyuteringizga tushiring (bu papkani ochib oling).
2. [IntelliJ IDEA](https://www.jetbrains.com/idea/) yoki VSCode + Java/Gradle
   plaginlari bilan oching (Fabric mod development uchun IntelliJ tavsiya etiladi).
3. Gradle avtomatik ravishda kerakli kutubxonalarni (Minecraft, Yarn mappings,
   Fabric API) yuklab oladi — internet kerak bo'ladi.
4. Terminalda: `./gradlew build` (Windows: `gradlew.bat build`)
5. Tayyor .jar fayl `build/libs/supermine-1.0.0.jar` da paydo bo'ladi.
6. Uni `.minecraft/mods` papkasiga, Fabric API jar bilan birga qo'ying.

> Eslatma: bu loyihada gradle wrapper fayllari (`gradlew`, `gradlew.bat`,
> `gradle/wrapper/*`) yo'q — birinchi marta ochganda IntelliJ IDEA "Gradle
> wrapper yaratasizmi" deb so'raydi, "Yes/OK" deb bosavering, yoki
> `gradle wrapper` komandasini qo'lda ishga tushiring (agar kompyuteringizda
> Gradle o'rnatilgan bo'lsa).

## Kodni o'zgartirish (masalan 6x6 emas, 8x8 qilish)
`SuperMineMod.java` faylida shu qatorni toping:

```java
for (int a = -2; a <= 3; a++) {
    for (int b = -2; b <= 3; b++) {
```

6x6 uchun `-2..3` oralig'i ishlatilgan (6 ta qiymat). Masalan 8x8 uchun
`-3..4` qiling.

## Tugmani default o'zgartirish
`SuperMineClient.java` faylida:

```java
GLFW.GLFW_KEY_B
```

qatorini boshqa tugmaga almashtiring (masalan `GLFW.GLFW_KEY_G`).
