import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.nio.file.*;

public class SafeHer {

    public static void main(String[] args) throws Exception {
        // Generate the HTML file
        String html = getHTML();

        // Save to temp file
        Path htmlFile = Files.createTempFile("safeher_", ".html");
        Files.writeString(htmlFile, html);

        System.out.println("SafeHer ChatPod is launching...");
        System.out.println("File: " + htmlFile.toAbsolutePath());

        // Open in default browser
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(htmlFile.toUri());
            System.out.println("Opened in browser successfully!");
        } else {
            System.out.println("Please open this file manually in your browser:");
            System.out.println(htmlFile.toAbsolutePath());
        }
    }

    private static String getHTML() {
        return """
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>SafeHer — Women Safety ChatPod</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@2.44.0/tabler-icons.min.css"/>
  <style>
    *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
    body {
      font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
      background: linear-gradient(135deg, #f5f0ff 0%, #ffe4f0 100%);
      min-height: 100vh;
      display: flex; align-items: center; justify-content: center;
      padding: 20px;
    }
    .app {
      display: flex; flex-direction: column;
      width: 480px; height: 700px;
      background: #ffffff;
      border-radius: 20px;
      box-shadow: 0 20px 60px rgba(127,119,221,0.25);
      overflow: hidden; position: relative;
    }
    .header {
      background: #7F77DD; padding: 16px 18px;
      display: flex; align-items: center; gap: 12px; flex-shrink: 0;
    }
    .hlogo {
      width: 38px; height: 38px; border-radius: 50%;
      background: rgba(255,255,255,0.2);
      display: flex; align-items: center; justify-content: center;
    }
    .hlogo i { font-size: 20px; color: white; }
    .hinfo h2 { color: white; font-size: 16px; font-weight: 600; }
    .hinfo p  { color: rgba(255,255,255,0.78); font-size: 12px; margin-top: 1px; }
    .sos-btn {
      margin-left: auto; background: #E24B4A; color: white; border: none;
      padding: 8px 16px; border-radius: 10px;
      font-size: 13px; font-weight: 600; cursor: pointer;
      display: flex; align-items: center; gap: 6px; transition: background 0.2s;
    }
    .sos-btn:hover { background: #c03a39; }
    .qbar {
      display: flex; gap: 8px; padding: 10px 14px; overflow-x: auto;
      border-bottom: 1px solid #f0eeff; background: #faf9ff; flex-shrink: 0;
    }
    .qbar::-webkit-scrollbar { display: none; }
    .qbtn {
      flex-shrink: 0; border: 1px solid #ddd8ff; background: #ffffff;
      color: #3a3580; font-size: 12px; padding: 5px 12px;
      border-radius: 20px; cursor: pointer; white-space: nowrap; transition: all 0.15s;
    }
    .qbtn:hover { background: #EEEDFE; border-color: #7F77DD; }
    .msgs {
      flex: 1; overflow-y: auto; padding: 16px 14px;
      display: flex; flex-direction: column; gap: 14px;
    }
    .msgs::-webkit-scrollbar { width: 4px; }
    .msgs::-webkit-scrollbar-thumb { background: #ddd8ff; border-radius: 4px; }
    .msg { display: flex; gap: 8px; max-width: 90%; }
    .msg.user { align-self: flex-end; flex-direction: row-reverse; }
    .avatar {
      width: 30px; height: 30px; border-radius: 50%; flex-shrink: 0;
      display: flex; align-items: center; justify-content: center; font-size: 15px;
    }
    .ba { background: #EEEDFE; color: #534AB7; }
    .ua { background: #E1F5EE; color: #0F6E56; }
    .bubble { padding: 10px 14px; border-radius: 16px; font-size: 14px; line-height: 1.65; }
    .bb { background: #f5f4ff; color: #1a1a2e; border-bottom-left-radius: 4px; border: 1px solid #eeeeff; }
    .ub { background: #7F77DD; color: white; border-bottom-right-radius: 4px; }
    .bubble ul { padding-left: 18px; margin: 6px 0; }
    .bubble li { margin: 5px 0; }
    .bubble p  { margin: 4px 0; }
    .bubble strong { font-weight: 600; }
    .num  { font-size: 16px; font-weight: 700; color: #E24B4A; }
    .tag  { display:inline-block; background:#FCEBEB; color:#791F1F; font-size:11px; padding:2px 8px; border-radius:20px; margin:2px 3px 2px 0; }
    .gtag { background:#E1F5EE; color:#085041; }
    .ptag { background:#EEEDFE; color:#26215C; }
    .typing-wrap { display: flex; gap: 8px; max-width: 90%; }
    .dots {
      display: flex; align-items: center; gap: 5px; padding: 12px 14px;
      background: #f5f4ff; border-radius: 16px; border-bottom-left-radius: 4px; border: 1px solid #eeeeff;
    }
    .dot { width: 7px; height: 7px; border-radius: 50%; background: #AFA9EC; animation: bop 1.2s infinite; }
    .dot:nth-child(2){animation-delay:.2s} .dot:nth-child(3){animation-delay:.4s}
    @keyframes bop{0%,60%,100%{transform:translateY(0)}30%{transform:translateY(-6px)}}
    .iarea {
      display: flex; gap: 8px; padding: 12px 14px;
      border-top: 1px solid #f0eeff; background: #fff; flex-shrink: 0;
    }
    .iarea input {
      flex: 1; padding: 10px 14px; border: 1px solid #ddd8ff;
      border-radius: 12px; font-size: 14px; background: #faf9ff;
      color: #1a1a2e; outline: none; transition: border-color 0.2s;
    }
    .iarea input:focus { border-color: #7F77DD; background: #fff; }
    .sbtn {
      background: #7F77DD; border: none; color: white;
      width: 40px; height: 40px; border-radius: 12px; cursor: pointer;
      display: flex; align-items: center; justify-content: center; flex-shrink: 0;
      transition: background 0.2s;
    }
    .sbtn:hover { background: #534AB7; }
    .sbtn i { font-size: 18px; }
    .overlay {
      position: absolute; inset: 0; background: rgba(0,0,0,0.55);
      display: none; align-items: center; justify-content: center;
      z-index: 10; border-radius: 20px;
    }
    .overlay.open { display: flex; }
    .sbox {
      background: #fff; border-radius: 18px; padding: 28px; width: 310px;
      text-align: center; box-shadow: 0 8px 32px rgba(0,0,0,0.18);
    }
    .sbox h3 { color: #E24B4A; font-size: 20px; font-weight: 700; margin-bottom: 6px; }
    .sbox p  { font-size: 13px; color: #666; margin-bottom: 22px; }
    .sgrid { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-bottom: 16px; }
    .scall {
      background: #fff5f5; border: 1px solid #f5c1c1; color: #791F1F;
      padding: 14px 8px; border-radius: 12px; cursor: pointer;
      font-size: 13px; font-weight: 600; transition: background 0.15s;
    }
    .scall span { display: block; font-size: 22px; font-weight: 700; color: #E24B4A; margin-bottom: 3px; }
    .scall:hover { background: #ffe5e5; }
    .sclose {
      background: #f5f4ff; border: 1px solid #ddd8ff; color: #534AB7;
      padding: 10px 20px; border-radius: 10px; cursor: pointer;
      font-size: 13px; font-weight: 600; width: 100%; transition: background 0.15s;
    }
    .sclose:hover { background: #eeedff; }
    .footer {
      text-align: center; font-size: 11px; color: #aaa;
      padding: 6px 0 10px; flex-shrink: 0; background: #fff;
    }
  </style>
</head>
<body>
<div class="app">
  <div class="header">
    <div class="hlogo"><i class="ti ti-shield-check"></i></div>
    <div class="hinfo">
      <h2>SafeHer</h2>
      <p>Your personal safety companion · Always here</p>
    </div>
    <button class="sos-btn" onclick="openSOS()">
      <i class="ti ti-alert-triangle"></i> SOS
    </button>
  </div>
  <div class="qbar">
    <button class="qbtn" onclick="ask('I feel unsafe right now')">🆘 I feel unsafe</button>
    <button class="qbtn" onclick="ask('emergency helpline numbers india')">📞 Helplines</button>
    <button class="qbtn" onclick="ask('tips for travelling alone at night')">🌙 Solo travel</button>
    <button class="qbtn" onclick="ask('best safety apps for women')">📱 Safety apps</button>
    <button class="qbtn" onclick="ask('self defence techniques for women')">🥋 Self-defence</button>
    <button class="qbtn" onclick="ask('how to share live location safely')">📍 Share location</button>
    <button class="qbtn" onclick="ask('legal rights for women in india')">⚖️ Legal rights</button>
    <button class="qbtn" onclick="ask('I am feeling anxious and scared')">💜 Mental health</button>
  </div>
  <div class="msgs" id="msgs"></div>
  <div class="iarea">
    <input id="inp" type="text" placeholder="Ask anything about safety, helplines, or support…"
           onkeydown="if(event.key==='Enter') send()"/>
    <button class="sbtn" onclick="send()"><i class="ti ti-send"></i></button>
  </div>
  <div class="footer">SafeHer · Women Safety ChatPod · Hackathon 2025</div>
  <div class="overlay" id="sos">
    <div class="sbox">
      <h3><i class="ti ti-alert-triangle"></i> Emergency SOS</h3>
      <p>Tap a number to call immediately</p>
      <div class="sgrid">
        <button class="scall" onclick="callNum('112')"><span>112</span>Emergency</button>
        <button class="scall" onclick="callNum('1091')"><span>1091</span>Women Helpline</button>
        <button class="scall" onclick="callNum('100')"><span>100</span>Police</button>
        <button class="scall" onclick="callNum('181')"><span>181</span>iCall</button>
      </div>
      <button class="sclose" onclick="closeSOS()">Close</button>
    </div>
  </div>
</div>
<script>
  const msgsEl = document.getElementById('msgs');
  const inpEl  = document.getElementById('inp');
  const KB = {
    unsafe: {
      keys: ['unsafe','danger','scared','help me','being followed','stalked','threatened','attack','harass','following me'],
      reply: () => `<p>🚨 <strong>Stay calm — you are not alone.</strong></p><ul><li><strong>Call 112</strong> right now if in immediate danger</li><li>Move toward a <strong>crowded, well-lit area</strong></li><li>Call a trusted person and <strong>stay on the line</strong></li><li>If followed, <strong>do NOT go home</strong> — enter any public space</li><li>Scream <strong>"FIRE!"</strong> instead of "help" — draws faster attention</li></ul><p style="margin-top:8px"><span class="tag">112 Emergency</span><span class="tag">1091 Women Helpline</span><span class="tag">100 Police</span></p>`
    },
    helplines: {
      keys: ['helpline','emergency number','contact','call','police number','women helpline','1091','112','numbers'],
      reply: () => `<p><strong>India emergency helplines for women:</strong></p><ul><li><span class="num">112</span> — National Emergency (all-in-one)</li><li><span class="num">1091</span> — Women Helpline (24×7, free)</li><li><span class="num">100</span> — Police</li><li><span class="num">108</span> — Ambulance</li><li><span class="num">181</span> — Domestic abuse & counselling</li><li><span class="num">7827-170-170</span> — iCall mental health</li><li><span class="num">1800-180-8080</span> — NHRC women's rights</li></ul><p style="margin-top:8px;font-size:13px;color:#666">Save these now — set 112 as speed dial.</p>`
    },
    travel: {
      keys: ['travel','night','alone','cab','auto','taxi','commute','bus','train','ola','uber','solo'],
      reply: () => `<p><strong>Staying safe while travelling alone at night:</strong></p><ul><li>Share your <strong>live location</strong> before leaving</li><li>Use <strong>Ola/Uber</strong> — note driver name & car number</li><li>Sit <strong>behind the driver</strong>, never in front</li><li>Keep <strong>phone charged</strong>, earphones out</li><li>Trust your gut — if something feels off, <strong>exit at a busy spot</strong></li><li><strong>Himmat Plus</strong> app lets Delhi Police track you</li></ul>`
    },
    apps: {
      keys: ['app','application','himmat','nirbhaya','bsafe','shake','raksha','phone','track','install'],
      reply: () => `<p><strong>Top safety apps for women in India:</strong></p><ul><li><span class="ptag">Himmat Plus</span> Delhi Police — shake to alert with live GPS</li><li><span class="ptag">Nirbhaya</span> — real-time location to trusted contacts</li><li><span class="ptag">bSafe</span> — fake call, siren alarm, GPS trail</li><li><span class="ptag">Shake2Safety</span> — shake phone to send emergency alert</li><li><span class="ptag">Raksha</span> — one-tap SOS to 5 contacts</li><li><span class="ptag">Smart24x7</span> — government-backed, works on 2G</li></ul><p style="font-size:13px;color:#666;margin-top:6px">Install one <em>before</em> you need it.</p>`
    },
    selfdefence: {
      keys: ['self defence','self-defence','defend','fight','protect','kick','punch','escape','grab','wrist','defence'],
      reply: () => `<p><strong>Essential self-defence moves:</strong></p><ul><li><strong>Palm strike</strong> — drive palm heel into nose or chin</li><li><strong>Knee to groin</strong> — when grabbed from front</li><li><strong>Wrist escape</strong> — rotate toward thumb, pull sharply</li><li><strong>Eye gouge</strong> — thumb pressure against choke hold</li><li><strong>Foot stomp</strong> — if grabbed from behind, stomp instep</li><li><strong>Elbow strike</strong> — strongest weapon at close range</li></ul><p style="margin-top:8px"><span class="gtag">Shout while striking</span><span class="gtag">Aim vulnerable points</span><span class="gtag">Run when free</span></p>`
    },
    location: {
      keys: ['location','share','live','gps','track','google maps','whatsapp location','trusted','share location'],
      reply: () => `<p><strong>How to share your live location:</strong></p><ul><li><strong>WhatsApp</strong> — Attach → Location → Share Live Location → pick duration</li><li><strong>Google Maps</strong> — Profile photo → Location Sharing → share with contact</li><li><strong>iPhone</strong> — Find My app → Share My Location</li><li><strong>Himmat / Nirbhaya</strong> — auto-share when SOS triggered</li></ul><p style="margin-top:8px">💡 Always message your route, ETA & cab number to a trusted contact before trips.</p>`
    },
    legal: {
      keys: ['law','legal','right','ipc','section','fir','complaint','court','justice','report','posh'],
      reply: () => `<p><strong>Key legal rights for women in India:</strong></p><ul><li><strong>File FIR anytime</strong> — police cannot refuse, demand a copy</li><li><strong>Section 498A IPC</strong> — domestic cruelty protection</li><li><strong>POSH Act 2013</strong> — workplace harassment protection</li><li><strong>Zero FIR</strong> — file at ANY police station</li><li><strong>FIR at home</strong> — police must come to you if requested</li><li><strong>Free legal aid</strong> — contact district DLSA</li></ul><p style="font-size:13px;color:#666;margin-top:6px">NCW portal: <strong>ncw.nic.in</strong> — 24×7 online complaints.</p>`
    },
    mental: {
      keys: ['anxious','anxiety','trauma','abuse','depressed','stress','mental','counsell','emotional','sad','overwhelmed'],
      reply: () => `<p>What you're feeling is completely valid — you don't have to face this alone. 💜</p><ul><li><strong>iCall (TISS)</strong> — <span class="num" style="font-size:14px">7827-170-170</span> — free counselling</li><li><strong>Vandrevala Foundation</strong> — <span class="num" style="font-size:14px">1860-2662-345</span> — 24×7</li><li><strong>iCall chat</strong> — icallhelpline.org — prefer typing</li></ul><p style="margin-top:8px">You deserve support. Reaching out is the bravest thing you can do.</p>`
    }
  };
  function matchIntent(text) {
    const t = text.toLowerCase();
    for (const [, v] of Object.entries(KB)) {
      if (v.keys.some(k => t.includes(k))) return v.reply();
    }
    return `<p>I want to give you the right help. Try asking about:</p><ul><li>🆘 I feel unsafe</li><li>📞 Helplines</li><li>🌙 Solo travel tips</li><li>📱 Safety apps</li><li>🥋 Self-defence</li><li>⚖️ Legal rights</li><li>💜 Mental health support</li></ul>`;
  }
  function escHtml(t) { return t.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;'); }
  function addMsg(role, html) {
    const isUser = role === 'user';
    const d = document.createElement('div');
    d.className = 'msg ' + role;
    d.innerHTML = `<div class="avatar ${isUser?'ua':'ba'}"><i class="ti ${isUser?'ti-user':'ti-shield-check'}"></i></div><div class="bubble ${isUser?'ub':'bb'}">${isUser?escHtml(html):html}</div>`;
    msgsEl.appendChild(d);
    msgsEl.scrollTop = msgsEl.scrollHeight;
  }
  function showTyping() {
    const d = document.createElement('div');
    d.className = 'typing-wrap'; d.id = 'typ';
    d.innerHTML = `<div class="avatar ba"><i class="ti ti-shield-check"></i></div><div class="dots"><div class="dot"></div><div class="dot"></div><div class="dot"></div></div>`;
    msgsEl.appendChild(d); msgsEl.scrollTop = msgsEl.scrollHeight;
  }
  function removeTyping() { const t=document.getElementById('typ'); if(t) t.remove(); }
  function send() {
    const text = inpEl.value.trim(); if (!text) return;
    inpEl.value = '';
    addMsg('user', text); showTyping();
    setTimeout(() => { removeTyping(); addMsg('bot', matchIntent(text)); }, 600 + Math.random()*400);
  }
  function ask(q) { inpEl.value = q; send(); }
  function openSOS()  { document.getElementById('sos').classList.add('open'); }
  function closeSOS() { document.getElementById('sos').classList.remove('open'); }
  function callNum(n) { window.location.href = 'tel:' + n; }
  addMsg('bot', `<p>Hi! I'm <strong>SafeHer</strong> — your personal women's safety companion 💜</p><p style="margin-top:6px">Ask me about emergency steps, helplines, solo travel safety, self-defence, legal rights, or safety apps.</p>`);
</script>
</body>
</html>
        """;
    }
}
