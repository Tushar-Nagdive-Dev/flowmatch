import 'zone.js/node';
import { APP_BASE_HREF } from '@angular/common';
import { CommonEngine } from '@angular/ssr';
import express from 'express';
import { dirname, join, resolve } from 'path';
import bootstrap from './src/main.server';

export function app(): express.Express {
  const server = express();

  // ✅ Use CommonJS __dirname directly (no import.meta.url)
  const browserDistFolder = resolve(__dirname, '../browser');
  const indexHtml = join(browserDistFolder, 'index.html');  

  const engine = new CommonEngine();

  server.set('view engine', 'html');
  server.set('views', browserDistFolder);

  server.get('*.*', express.static(browserDistFolder, {
    maxAge: '1y'
  }));

  server.get('*', (req, res, next) => {
    engine
      .render({
        bootstrap,
        documentFilePath: indexHtml,
        url: req.originalUrl,
        publicPath: browserDistFolder,
        providers: [{ provide: APP_BASE_HREF, useValue: req.baseUrl }]
      })
      .then(html => res.send(html))
      .catch(err => next(err));
  });

  return server;
}

function run(): void {
  const port = process.env['PORT'] || 4000;
  const serverInstance = app();
  serverInstance.listen(port, () => {
    console.log(`✅ SSR server running at http://localhost:${port}`);
  });
}

run();
