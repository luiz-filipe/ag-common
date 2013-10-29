package org.ag.test.common.renderer;

import org.ag.common.renderer.RenderedImage;
import org.ag.common.renderer.SingleRendererImageWriter;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingleRendererImageWriterTest {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final String path = "target";

    @Test
    public void writeTest() {
        final String imageName = "single-renderer-write.png";
        final RenderedImage image = new RenderedImage(imageName, new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB));

        executor.submit(new SingleRendererImageWriter(path, image));

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final File f = new File(path + File.separator + imageName);

        Assert.assertTrue(f.exists());
    }
}
