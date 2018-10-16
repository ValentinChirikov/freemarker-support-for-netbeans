package org.netbeans.freemarker.lexer;

import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.html.lexer.HTMLTokenId;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageProvider;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = LanguageProvider.class)
public class HTMLEmbeddingLanguageProvider extends LanguageProvider {

    private Language embeddedLanguage;

    @Override
    public Language<?> findLanguage(String mimeType) {
        return HTMLTokenId.language();
    }

    /**
     * Finds <code>LanguageEmbedding</code> that will define what language is
     * embedded in a given token.
     *
     * <p>
     * If a <code>Token</code> contains text in a different language that could
     * further be used for lexing of this <code>Token</code> the framework will
     * try to find out the <code>Language</code> of that language by asking the
     * <code>Token</code>'s own <code>Language</code> first and then by
     * consulting registered <code>LanguageProvider</code>s. The
     * <code>LanguageProvider</code>s are expected to return a
     * <code>LanguageEmbedding</code> for tokens they care about and
     * <code>null</code> for the rest. The first non-null
     * <code>LanguageEmbedding</code> found will be used.
     *
     * <p>
     * <code>LanguageEmbedding</code> instances returned from this method
     * <b>must not</b> reference any of the attributes passed in and especially
     * not the <code>token</code> instance.
     *
     * @param token The <code>Token</code> to get the <code>Language</code> for.
     * @param languagePath The <code>LanguagePath</code> of the token, which
     * embedded language should be returned.
     * @param inputAttributes The attributes that could affect the creation of
     * the embedded <code>Language</code>. It may be <code>null</code> if there
     * are no extra attributes.
     *
     * @return The <code>LanguageEmbedding</code> for the given
     * <code>Token</code> or <code>null</code> if the token can't embedd any
     * language or the token is unknown to this <code>LanguageProvider</code>.
     */
    @Override
    public LanguageEmbedding<?> findLanguageEmbedding(Token<?> token, LanguagePath languagePath, InputAttributes inputAttributes) {
        initLanguage();//prepare Html language

        if (token.id().name().startsWith("STATIC_TEXT")) {//check if it eats comments
            return LanguageEmbedding.create(embeddedLanguage, 0, 0, true);//we will join embedded language section over all doc
        }

        return null;
    }

    private void initLanguage() {
        embeddedLanguage = MimeLookup.getLookup("text/html").lookup(Language.class);
        if (embeddedLanguage == null) {
            throw new NullPointerException("Can't find HTML language for embedding");
        }

    }

}
